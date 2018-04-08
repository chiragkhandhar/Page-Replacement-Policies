/*
=============================================================
Name: Chirag Khandhar
Class: TE-1(C)
Roll No.: 305069
Assg. No.: Group D-1
=============================================================
*/
import java.util.Scanner;
import java.util.*;
class replacement
{
	String process[],frame[];
	int fsize,no,faults;
	
	void getData()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter no. of processes: ");
		no=sc.nextInt();
		process=new String[no];
		
		System.out.println("Enter Frame Size");
		fsize=sc.nextInt();
		
		
		System.out.println("Enter the Sequence of occuring processes");
		for(int i=0;i<no;i++)
		{
			System.out.println("> Process ID: ");
			process[i]=sc.next();
		}
	}
	
	int isVacant(String temp)
	{
		for(int i=0;i<fsize;i++)
		{
			if(frame[i]==null)
			{
				frame[i]=temp;
				faults++;
				return i;
			}
		}
		return -1;
	}
	
	int isPageHit(String temp)
	{
		for(int i=0;i<fsize;i++)
			if(frame[i].equals(temp))
				return i;
		return -1;
	}
	
	void printFrame( boolean faultCheck)
	{
		for(int i=0;i<fsize;i++)
		{
			if(frame[i]==null)
				System.out.print("|   |");
			else
				System.out.print("| "+frame[i]+" |");
		}
		if(faultCheck==true)				//fault has occurred
			System.out.print(" F"+faults);
	}
	
	void FIFO()
	{
		String temp;
		int hit_flag=-1, vacancy_flag=-1,frameIndex=0;
		boolean faultCheck;
		frame=new String[fsize];
		faults=0;
		System.out.println("=====================================");
		System.out.println("------First In First Out (FIFO)------\n");
		for(int i=0;i<no;i++)
		{
			temp=process[i];
			vacancy_flag=isVacant(temp);
			if(vacancy_flag==-1)					// if true: no empty space in frame
			{
				hit_flag=isPageHit(temp);
				if(hit_flag==-1)				//if true: no hit found
				{
					frame[frameIndex]=temp;
					faults++;
					frameIndex++; 
					if(frameIndex==fsize)		// roll-over frameIndex
						frameIndex=0;
				}
			}
			
			if(vacancy_flag!=-1 || hit_flag==-1)		//fault occurrence check
				faultCheck=true;
			else
				faultCheck=false;
				
			printFrame(faultCheck);
			System.out.println();
		}
		System.out.println("\nTotal Faults: "+faults);
		System.out.println("====================================");
	}
	
	int findLRUIndex(String temp,int currentIndex)
	{
		LinkedList<String> al=new LinkedList<String>();  
		
		for(int i=0;i<fsize;i++)
			al.add(frame[i]);					// adds frame elements to the linkedlist
		
		while(al.size()!=1)
		{
			if(al.contains(process[currentIndex]))
				al.remove(process[currentIndex]);
			currentIndex--;
		}		
		return isPageHit(al.getFirst());	//gives the index of left over element in the LL
	}
	
	void LRU()
	{
		String temp;
		int hit_flag=-1, vacancy_flag=-1,frameIndex=0;
		boolean faultCheck;
		frame=new String[fsize];
		faults=0;
		System.out.println("=====================================");
		System.out.println("------Least Recently Used (LRU)------\n");
		for(int i=0;i<no;i++)
		{
			temp=process[i];
			vacancy_flag=isVacant(temp);
			if(vacancy_flag==-1)					// if true: no empty space in frame
			{
				hit_flag=isPageHit(temp);
				if(hit_flag==-1)				//if true: no hit found
				{
					frameIndex=findLRUIndex(temp,i-1);
					frame[frameIndex]=temp;
					faults++;
				}
			}
			
			if(vacancy_flag!=-1 || hit_flag==-1)		//fault occurrence check
				faultCheck=true;
			else
				faultCheck=false;
				
			printFrame(faultCheck);
			System.out.println();
		}
		System.out.println("\nTotal Faults: "+faults);
		System.out.println("====================================");
		}
	
	int findOptimalIndex(String temp,int currentIndex)
	{
		LinkedList<String> al=new LinkedList<String>();  
		
		for(int i=0;i<fsize;i++)
			al.add(frame[i]);					// adds frame elements to the linkedlist
		
		
			while(al.size()!=1)
			{
				if(currentIndex<no)
				{
					if(al.contains(process[currentIndex]))
						al.remove(process[currentIndex]);
					currentIndex++;
				}
				else
					break;
			}
		return isPageHit(al.getFirst());	//gives the index of left over element in the LL
	}
	
	void optimal()
	{
		String temp;
		int hit_flag=-1, vacancy_flag=-1,frameIndex=0;
		boolean faultCheck;
		frame=new String[fsize];
		faults=0;
		System.out.println("=====================================");
		System.out.println("---------------Optimal--------------\n");
		for(int i=0;i<no;i++)
		{
			temp=process[i];
			vacancy_flag=isVacant(temp);
			if(vacancy_flag==-1)					// if true: no empty space in frame
			{
				hit_flag=isPageHit(temp);
				if(hit_flag==-1)				//if true: no hit found
				{
					frameIndex=findOptimalIndex(temp,i+1);
					frame[frameIndex]=temp;
					faults++;
				}
			}
			
			if(vacancy_flag!=-1 || hit_flag==-1)		//fault occurrence check
				faultCheck=true;
			else
				faultCheck=false;
				
			printFrame(faultCheck);
			System.out.println();
		}
		System.out.println("\nTotal Faults: "+faults);
		System.out.println("====================================");
		}
	}

public class D1 
{

	public static void main(String[] args) 
	{
		int choice;
		boolean cont= true;
		replacement obj=new replacement();
		Scanner sc= new Scanner(System.in);
		System.out.println("====================================");
		System.out.println("Page Replacement Policies Simulation");
		System.out.println("====================================");
		
		while(cont)
		{
			System.out.println("\tEnter Choice\n\n1. Enter Data\n2. First In First Out\n3. Least Recently Used\n4. Optimal\n5. Exit\n--> ");
			choice=sc.nextInt();
				
			switch(choice)
			{
			case 1:
				obj.getData();
				break;
			case 2:
				obj.FIFO();
				break;
			case 3:
				obj.LRU();
				break;
			case 4:
				obj.optimal();
				break;
			case 5:
				System.out.println("----------------------------------------");
				System.out.print("\t\tExiting...");
				System.out.println("\n----------------------------------------");
				cont=false;
				break;
			default:
				System.out.println("Invalid Choice!\n---------------Exiting---------------");
				cont=false;
				break;
					
			}// end of switch
		}// end of while
	}
}
