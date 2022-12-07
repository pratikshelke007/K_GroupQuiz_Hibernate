package com.quiz;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import custom_exceptions.QuizException;
import entity.Marks;
import entity.Student;
import utils.ValidationRules;

public class App 
{

	static int choice=0;

	public static void main( String[] args ) 
	{
		//to off hibernate info n comments on console
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		
		Session session=Operations.getSession();


		while(choice!=5) {

			System.out.println("\n\n\t\t\t\t-----CHOOSE AN OPTION------\n\n\t\t\t\t1.Play quiz\n\t\t\t\t2.Show Student Information\n\t\t\t\t3.Display marks of all students \n\t\t\t\t4.Display marks by student id\n\t\t\t\t5.Exit\n");

			try {

				Scanner sc1 = new Scanner(System.in);
				System.out.print("\t\t\t\t");
				choice=sc1.nextInt();

			}catch(InputMismatchException e) {

				System.out.println("\n\t\t\t\tPlease enter number option from (1-4)!!\n");
				choice=0;

				continue;

			}


			switch(choice) {

			case 1: 
				boolean flag=true;
				int fnameCount=0;
				int lnameCount=0;
				int idCount=0;
				int emailCount=0;
				String fname=null;
				String lname=null;
				String email=null;
				int id=0;

				System.out.println("\t\t\t\tRegister Student Details :-");

				while(flag==true) {
					Scanner sc = new Scanner(System.in);


					try {
						if(fnameCount==0) {

							System.out.println("\n\n\t\t\t\tEnter First Name");
							System.out.print("\t\t\t\t");
							fname=ValidationRules.nameValidation(sc.next());
							fnameCount=1;
						}

						if(lnameCount==0) {
							System.out.println("\n\n\t\t\t\tEnter Last Name");
							System.out.print("\t\t\t\t");
							lname=ValidationRules.nameValidation(sc.next());
							lnameCount=1;
						}

					} catch (QuizException e1) {
						e1.printStackTrace();
						continue;
					}


					if(idCount==0) {
						System.out.println("\n\n\t\t\t\tEnter Student Id");
						System.out.print("\t\t\t\t");
						if(sc.hasNextInt()) {
							id=sc.nextInt();
							Student std=session.get(Student.class, id);
							if(std!=null) {
								System.out.println("\t\t\t\tId already present. Please Enter new Id!");
								continue;
							}
							idCount=1;

						}else {
							try {
								throw new QuizException("Invalid id: Enter number only!");
							} catch (QuizException e) {
								e.printStackTrace();
								continue;
							}
						}
					}


					if(emailCount==0) {
						System.out.print("\n\n\t\t\t\t");
						System.out.println("Enter E-mail id");

						try {
							System.out.print("\t\t\t\t");
							email = ValidationRules.emailValidation(sc.next());
							emailCount=1;
						} catch (QuizException e) {
							e.printStackTrace();
							continue;
						}

					}
					flag=false;
				}
				System.out.println();

				//question display
				System.out.println("\n\t\t\t\tQuiz Started!!\n");
				Marks marks=new Marks();
				int m=Operations.displayQuestion(id);
				marks.setMarks(m);


				Operations.insertStudent(new Student(id,fname,lname,email,marks));

				break;

			case 2:
				System.out.println("\n\t\t\t\tDisplay Student Info: Namewise");

				Operations.showStudentInfo();//show student details

				break;


			case 3:
				System.out.println("\n\t\t\t\tDisplay Marks of all Students");

				Operations.showStudentMarks();

				break;

			case 4: 
				System.out.println("\n\t\t\t\tDisplay Marks of Students by id");
				System.out.print("\t\t\t\tEnter Id>>");
				Scanner sc3=new Scanner(System.in);

				int getId=0;
				if(sc3.hasNextInt()) {
					getId=sc3.nextInt();
					Operations.showStudentMarksbyId(getId);

				}else {
					try {
						throw new QuizException("Invalid id");
					} catch (QuizException e) {
						e.printStackTrace();
						continue;
					}
				}

				break;

			case 5://exit
				System.out.println("\t\t\t\tThanks for Playing!!");
				break;

			default :
				System.out.println("\n\t\t\t\tPlease select choice from (1-4)!!\n");
				break;

			}


		}




	}


}
