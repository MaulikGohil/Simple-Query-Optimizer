/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimiserquerysql;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OptimiserQueryConfigureBox extends JFrame implements ActionListener
{
    JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, errorlbl;
    JTextField tabl1tup, table1page, tabl2tup,table2page,tabl3tup,table3page, buff1, buff2 ;
    JButton btnProcess;
    public static JTextArea textArea, resultArea, query1, query2;
    
    public static int bufferMemoryWithBuff = 50;
    public static int bufferMemoryWithlessBuff = 30;
    
    public static int t1TupPerPage;
    public static int t2TupPerPage;
    public static int t3TupPerPage;
    
    public static final int pageSize=4096;
    public static final int blockSize=100;

    public static int totT1;
    public static int totT2;
    public static int totT3;
    
    static String output="";
    public static boolean isSortergeDone = false;
        
    //configuration box
    public OptimiserQueryConfigureBox()
    {
		  	
        setSize(1250, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Group Project");
		 
        lbl1 = new JLabel("Query Optimiser");
        lbl1.setForeground(Color.BLACK);
        lbl1.setFont(new Font("Serif", Font.BOLD, 16));
	
        query1 = new JTextArea();
        query1.setBounds(80, 100, 250, 200);
        query1.setVisible(true);
        query1.setForeground(Color.BLACK);
        query1.setFont(new Font("Serif", Font.BOLD, 14));
        
        
        query2 = new JTextArea();
        query2.setBounds(360, 100, 250, 200);
        query2.setVisible(true);
        query2.setForeground(Color.BLACK);
        query2.setFont(new Font("Serif", Font.BOLD, 12));
        
        lbl8 = new JLabel("Simple Query :");
        lbl9 = new JLabel("Optimise Query :");
        lbl8.setBounds(80, 70, 250, 30);
        lbl9.setBounds(360, 70, 250, 30);
        
        lbl2 = new JLabel("tuple size:");
        lbl3 = new JLabel("pages:");
        lbl2.setBounds(250, 320, 200, 30);
        lbl3.setBounds(450, 320, 200, 30);
        
        lbl4 = new JLabel("Table 1:");
        lbl4.setBounds(80, 360, 200, 30);
        
        lbl5 = new JLabel("Table 2:");
        lbl5.setBounds(80, 400, 200, 30);
        
        lbl6 = new JLabel("Table 3:");
        lbl6.setBounds(80, 440, 200, 30);
                
        lbl7 = new JLabel("Memory with buffer:");
        lbl7.setBounds(80, 480, 200, 30);

        lbl10 = new JLabel("Memory with less buffer:");
        lbl10.setBounds(80, 520, 200, 30);        
        
        
        tabl1tup = new JTextField();
        table1page = new JTextField();
        tabl2tup = new JTextField();
        table2page = new JTextField();
		        
        tabl3tup = new JTextField();
        table3page = new JTextField();
        
        buff1 = new JTextField();
        buff2 = new JTextField();
        
        tabl1tup.setBounds(250, 360, 150, 30);
        table1page.setBounds(450, 360, 150, 30);
        tabl2tup.setBounds(250, 400, 150, 30);
        table2page.setBounds(450, 400, 150, 30);
        tabl3tup.setBounds(250, 440, 150, 30);
        table3page.setBounds(450, 440, 150, 30);
        
        buff1.setBounds(250, 480, 150, 30);
        buff2.setBounds(250, 520, 150, 30);
        buff1.setText("50");
        buff2.setText("30");
       
	
        errorlbl = new JLabel();
        btnProcess = new JButton("Process the Query");
        textArea = new JTextArea(5, 20);
	
        //output box
        resultArea = new JTextArea();
        resultArea.setBounds(650, 30, 550, 500);
        resultArea.setFont(new Font("Serif", Font.BOLD, 14));
        resultArea.setVisible(true);
             
        
        add(lbl1);
        add(query1);
        add(query2);
        add(lbl8);
        add(lbl9);
        add(lbl2);
        add(lbl3);
        add(lbl4);
        add(lbl5);
        add(lbl6);
        add(lbl7);
        add(lbl10);
        
        add(buff1);
        add(buff2);
        add(tabl1tup);
        add(table1page);
        add(tabl2tup);
       add(table2page);
        add(tabl3tup);
        add(table3page);
        add(btnProcess);
        
        add(textArea);
        add(errorlbl);
        add(resultArea);
        
        btnProcess.addActionListener(this);
      		 
        lbl1.setBounds(100, 30, 500, 30);
     
        
        errorlbl.setBounds(80, 620, 200, 30);
        errorlbl.setForeground(Color.RED);
        
       
        btnProcess.setBounds(80, 570, 300, 50);
               
        setVisible(true);
		    
} 
    public void actionPerformed(ActionEvent e) 
    { 
       if (e.getSource() == btnProcess)
       {
           if(buff1.getText().equals("") || buff2.getText().equals("") || query1.getText().equals("") || query2.getText().equals("") || tabl1tup.getText().equals("") || table1page.getText().equals("") || tabl2tup.getText().equals("") || table2page.getText().equals("") || tabl3tup.getText().equals("") || table3page.getText().equals(""))
            {
                errorlbl.setText("Please enter the all fileds...");
                return;
            }
           
            bufferMemoryWithBuff = Integer.parseInt(buff1.getText());
            bufferMemoryWithlessBuff = Integer.parseInt(buff2.getText());
            
            processTheQuery(Integer.parseInt(tabl1tup.getText()),Integer.parseInt(table1page.getText()),Integer.parseInt(tabl2tup.getText()),Integer.parseInt(table2page.getText()),Integer.parseInt(tabl3tup.getText()),Integer.parseInt(table3page.getText()), resultArea);
        }
       else
       {
           //clear button clicks
           tabl1tup.setText("");
           table1page.setText("");  
           tabl2tup.setText("");
           table2page.setText("");
           tabl3tup.setText(""); 
           table3page.setText("");
       }
    }
        
    public static void main(String[] args)
    {
        OptimiserQueryConfigureBox conf = new OptimiserQueryConfigureBox();
    }	
    
    public void processTheQuery(int t1TupleSize,int t1Pages,int t2TupleSize,int t2Pages,int t3TupleSize,int t3Pages, JTextArea resultArea)
        {
            //clear the text area
            resultArea.setText("");
            
            totT1 = (t1Pages*pageSize)/t1TupleSize;
            totT2 = (t2Pages*pageSize)/t2TupleSize;
            totT3 = (t3Pages*pageSize)/t3TupleSize;

            t1TupPerPage = pageSize/t1TupleSize;
            t2TupPerPage = pageSize/t2TupleSize;
            t3TupPerPage = pageSize/t3TupleSize;

           

            //calculate Q1 cost 
            int cost1 = processQ1(t1Pages,t2Pages,t3Pages);
            int cost2 = processRQ1(t1Pages,t2Pages,t3Pages);
            
            displayOutPut(cost1, cost2);
	}

        private static void displayOutPut(int cost1, int cost2)
        {
            try
            {
                
                
                if(cost1>cost2)
                {
                output = output + "****************************************************************************************\n";
                System.out.println();
                output = output + "The Best Query Plan is RQ1 since Cost of RQ1 < Cost of Q1\n";
                }
                else
                {
                    output = output + "****************************************************************************************\n";
                    System.out.println();
                    output = output + "The Best Query Plan is Q1 since Cost of RQ1 > Cost\n of Q1";
                }
                resultArea.setText(output);	
            }
            catch(Exception ex)
            {
            
            }
        }
    
	private static void findMinimumJoinCost(int leftTable, int rightTable, SQLProcess obj) {
		
            try
            {
                int cost = 0;
                //finding minimum cost 
		cost = TNLJoinCost(leftTable, rightTable, t1TupPerPage, obj);
		cost = PNLJoinCost(leftTable, rightTable, obj);
		cost = hashJoinCostWithBuffer(leftTable, rightTable, obj);
		cost = hashJoinCostWithLessBuffer(leftTable, rightTable, obj);
		cost = SMJJoinCostWithBuf(leftTable, rightTable, obj); 
		cost = SMJJoinCostWithLessBuf(leftTable, rightTable, obj);
                cost = BNLJoinCostWithBuf(leftTable, rightTable, obj);
		cost = BNLJoinCostWithLessBuf(leftTable, rightTable, obj);
            }
            catch(Exception ex)
            {
                
            }
	}

	
	//Group by (with Aggregation on the fly at the last scan) is Sorting cost
	// Both Sorting base and Hash based algorithms for Group By costs 3(M + N)
	//If last step is Sort Merge Join then Group By Cost is 2(M+N)
	public static int GetGroupByCost(int table1, int table2, boolean isSMJDone){
	try
        {
		if(isSMJDone){
			return 2*(table1+table2);
		}else{
			return 3*(table1+table2);
		}
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}
	
	public static int joinTables(int table1, int table2){
		return table1*table2;
	}
	
        //Query Processing Cost = Disk I/O Cost
	//		= # of Disk I/O * Disk Access Time
	//		= # of Disk I/O * (8 ms + 4 ms)
	//		= Total # of Disk Block access needed * 12ms
	public static String queryProcessingCost(int diskIOCost)
        {
		try
                {
		BigInteger ioCost = BigInteger.valueOf(diskIOCost).multiply(BigInteger.valueOf(12)).divide(BigInteger.valueOf(1000));
		int totalSeconds = ioCost.intValue();
		int seconds = totalSeconds % 60;
                int totalMinutes = totalSeconds / 60;
                int minutes = totalMinutes % 60;
                int hours = totalMinutes / 60;
                return hours + " hr(s) " + minutes + " min(s) " + seconds + " sec(s)";
                }
                catch(Exception ex)
                {
                    
                }
                return "";
	}
	
	public static int processQ1(int t1Pages,int t2Pages,int t3Pages){
            try
            {
                output = output + "Q1 cost is as follows:\n";
                
                output = output + "****************************************************************************************\n";
                
		int totalCost = 0;
			for (String line : query1.getText().split("\\n")) {
				int tempTable = joinTables(t1Pages, t2Pages);
				
				if (line.toLowerCase().contains("join")) {
					String[] elements = line.split(" ");
					String leftTable = elements[1];
					String rightTable = elements[2];
					if(leftTable.equals("t1") && rightTable.equals("t2")){
						if(t1Pages > t2Pages){
							SQLProcess select = new SQLProcess();
							findMinimumJoinCost(t2Pages, t1Pages, select);
							totalCost += select.joinCost;
                                                output = output + elements[0];
						if(select.isReverseOrder){
							output = output + " "+elements[2]+" "+elements[1];
						}else{
							output = output + " "+elements[1]+" "+elements[2];
						}
                                                 output = output + "-->Cost: "+select.joinCost+" -- Type of Join: "+select.joinType+ "\n";
						}
					}else if(leftTable.equals("temp1")){
						SQLProcess select2 = new SQLProcess();
						TNLJoinCost(t3Pages, tempTable, t3TupPerPage, select2);
						totalCost += select2.joinCost;
						 output = output + elements[0];
						if(select2.isReverseOrder){
							output = output + " "+elements[2]+" "+elements[1];
						}else{
							output = output + " "+elements[1]+" "+elements[2];
						}
                                                 output = output + "-->Cost: "+select2.joinCost+" -- Type of Join: " + select2.joinType+ "\n";
						}
					
				}
				if (line.toLowerCase().contains("project")) {
					String[] elements = line.split(" ");
					output = output + elements[0]+" "+elements[1]+"-->Cost is too small so we can neglect it\n";
				}
				if (line.toLowerCase().contains("groupby")) {
					String[] elements = line.split(" ");
					int groupByCost = GetGroupByCost(tempTable, t3Pages, false);
					totalCost+=groupByCost;
					output = output + elements[0]+" "+elements[1]+"-->Cost: "+groupByCost+"\n";
				}
					
			}
			output = output + "Total Disk I/O Cost is: "+totalCost+"\n";
			output = output + "Query Processing Cost: "+queryProcessingCost(totalCost)+"\n";
			
		
		return totalCost;
            }
            catch(Exception ex)
            {
                
            }
            return 0;
	}
        
	
	public static int processRQ1(int t1Pages,int t2Pages,int t3Pages){
            try
            {
                System.out.println();
                output = output + "RQ1 cost is as follows:\n";
                output = output + "****************************************************************************************\n";
		int totalQueryProcessingCost = 0;
		int tempTable = 0;
		
                for (String line : query2.getText().split("\\n")) {
				
				if (line.toLowerCase().contains("join")) {
					String[] elements = line.split(" ");
					String leftTable = elements[1];
					String rightTable = elements[2];
					if(leftTable.equals("t1") && rightTable.equals("t3")){
						if(t3Pages > t1Pages){
							SQLProcess select = new SQLProcess();
							findMinimumJoinCost(t3Pages, t1Pages, select);
							tempTable = (joinTables(t3Pages, t1Pages)*10)/100;
							totalQueryProcessingCost+=select.joinCost;
							if(select.isReverseOrder){
							output = output + " "+elements[2]+" "+elements[1];
						}else{
							output = output + " "+elements[1]+" "+elements[2];
						}
                                                 output = output + "-->Cost: "+select.joinCost+" -- Type of Join: "+select.joinType+ "\n";
						
						}
					}else if(leftTable.equals("t1")){
						SQLProcess select2 = new SQLProcess();
						findMinimumJoinCost(t1Pages, tempTable, select2);
						tempTable = (tempTable*10)/100; //selectivity 10%
						totalQueryProcessingCost+=select2.joinCost;
						if(select2.isReverseOrder){
							output = output + " "+elements[2]+" "+elements[1];
						}else{
							output = output + " "+elements[1]+" "+elements[2];
						}
                                                 output = output + "-->Cost: "+select2.joinCost+" -- Type of Join: "+select2.joinType+ "\n";
						
					}else if(leftTable.equals("t2")){
						SQLProcess select2 = new SQLProcess();
						findMinimumJoinCost(t2Pages, tempTable, select2);
						tempTable = (tempTable*10)/100; //selectivity 10%
						totalQueryProcessingCost+=select2.joinCost;
						if(select2.isReverseOrder){
							output = output + elements[0]+" "+elements[2]+" "+elements[1]+"-->Cost: "+select2.joinCost+" -- Type of Join: "+select2.joinType+ ""+"\n";
						}else{
							output = output + elements[0]+" "+elements[1]+" "+elements[2]+"-->Cost: "+select2.joinCost+" -- Type of Join: "+select2.joinType+"\n";
						}
						}
					
				}
				if (line.toLowerCase().contains("project")) {
					String[] elements = line.split(" ");
					output = output + elements[0]+" "+elements[1]+"-->Cost is too small so we can neglect it\n";
				}
				if (line.toLowerCase().contains("groupby")) {
					String[] elements = line.split(" ");
					int groupByCost = 0;
					if(elements[1].equals("temp0")){
						groupByCost = GetGroupByCost(t1Pages, t3Pages, isSortergeDone);
					} else{
						groupByCost = GetGroupByCost(tempTable, t3Pages, isSortergeDone);
					}
					totalQueryProcessingCost+=groupByCost;
					System.out.println(elements[0]+" "+elements[1]+"-->Cost: "+groupByCost);
				}
					
			}
			output = output + "Total Disk I/O Cost is: "+totalQueryProcessingCost+"\n";
			output = output + "Query Processing Cost: "+queryProcessingCost(totalQueryProcessingCost)+"\n";
		
		return totalQueryProcessingCost;
            }
            catch(Exception ex)
            {
                
            }
            return 0;
	}
	
        //cost calculating function
        //For each tuple in the outer relation scan the entire inner relation.
	//Cost: M (to scan R) + (pR * M) times * N (to scan S)
        public static int TNLJoinCost(int leftTable, int rightTable, int tuplesPerPage, SQLProcess obj){
	try
        {
		
                //calulate the cost
		int cost = ( leftTable + tuplesPerPage * leftTable * rightTable);
                
		if((cost>=0))
                {
                    obj.joinCost = cost;
                    obj.joinType = "TNL";
                    obj.isReverseOrder = true;
		}
		return cost;
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}
	
        //Page-oriented Nested Loops join
	//Cost: M (to scan R) + M times *N 
	public static int PNLJoinCost(int leftTable, int rightTable, SQLProcess obj){
	try
        {
                int cost = leftTable + leftTable*rightTable;
                if(obj.joinCost > cost)
                { 
                    obj.joinCost = cost;
                    obj.joinType = "PNL";
                }
		return cost;
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}

        //Block-oriented Nested Loops join
	//Cost of scanning R is 1000 I/Os; a total of 10 blocks
	//Per block of R, we scan Sailors (S); 10*500 I/Os. Total = 1000 + 10*500
	public static int BNLJoinCostWithBuf(int leftTable, int rightTable, SQLProcess obj){
		try
                {
		int bufferMemory = bufferMemoryWithBuff;
		int cost = (leftTable+(leftTable/bufferMemory)*rightTable);
		if(obj.joinCost>cost){
			obj.joinCost=cost;
			obj.joinType = "BNL buffer = 50";
		}
		return cost;
                }
                catch(Exception ex)
                {
                    
                }
                return 0;
	}
	
        //Block-oriented Nested Loops join
	//Cost of scanning R is 1000 I/Os; a total of 10 blocks
	//Per block of R, we scan Sailors (S); 10*500 I/Os. Total = 1000 + 10*500
	//Buffer Memory is considered as 30 pages
	public static int BNLJoinCostWithLessBuf(int leftTable, int rightTable, SQLProcess obj){
	try
        {
		int bufferMemory = bufferMemoryWithlessBuff;
		int cost = (leftTable+(leftTable/bufferMemory)*rightTable);
		if(obj.joinCost>cost){
			obj.joinCost=cost;
			obj.joinType = "BNL buffer = 30";
			isSortergeDone = true;
			obj.isReverseOrder = false;
		}
		return cost;
        }
        catch(Exception ex)
        {
            
        } 
        return 0;
	}
	
        //SMJ has a cost of:
	//2M(1+ [logB-1 [M/B]]) + 2N(1+ [logB-1 [N/B]]) + M + N
	//Buffer Memory is considered as 50 pages
	public static int SMJJoinCostWithBuf(int leftTable, int rightTable, SQLProcess obj){
	try
        {
            int cost;
		
			int bufferMemory = bufferMemoryWithBuff;
			double mMultiplier = (Math.log10(leftTable/bufferMemory))/(Math.log10(bufferMemory-1));
			double nMultiplier = (Math.log10(rightTable/bufferMemory))/(Math.log10(bufferMemory-1));
			
			cost = (int)Math.ceil(2*leftTable*(1+mMultiplier)+2*rightTable*(1+nMultiplier)+leftTable+rightTable);
			//hmap.put(cost, "Sort Merge Join with Buffer="+bufferMemory);
			if(obj.joinCost>cost){
				obj.joinCost=cost;
				obj.joinType = "SMJ buffer = 50";
				obj.isReverseOrder = false;
				isSortergeDone = true;
			}
		return cost;
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}
	
        //SMJ has a cost of:
	//2M(1+ [logB-1 [M/B]]) + 2N(1+ [logB-1 [N/B]]) + M + N
	//Buffer Memory is considered as 30 pages
	public static int SMJJoinCostWithLessBuf(int leftTable, int rightTable, SQLProcess obj){
            try
            {
            int cost;
		
			int bufferMemory = bufferMemoryWithlessBuff;
			double mMultiplier = Math.log10(leftTable/bufferMemory)/Math.log10(bufferMemory-1);
			double nMultiplier = Math.log10(rightTable/bufferMemory)/Math.log10(bufferMemory-1);
			
			cost = (int)Math.ceil((2*leftTable*(1+mMultiplier)+2*rightTable*(1+nMultiplier)+leftTable+rightTable));
			//hmap.put(cost, "Sort Merge Join with Less Buffer="+bufferMemory);
			if(obj.joinCost>cost){
				obj.joinCost=cost;
				obj.joinType = "SMJ buffer = " + bufferMemory;
				obj.isReverseOrder = false;
				isSortergeDone = true;
			}
		return cost;
            }
            catch(Exception ex)
            {
                
            }
            return 0;
	}
	
        //Total Cost: 3(M + N)
	//But for specified buffer memory = 50
	//Total Cost: 2 * (M+N) * (1+logB-1(M+N)/B-1) + M + N
	public static int hashJoinCostWithBuffer(int leftTable, int rightTable, SQLProcess obj){
	try
        {
		
		int bufferMemory = bufferMemoryWithBuff;
		double multiplier = Math.log10((leftTable+rightTable)/(bufferMemory-1))/(Math.log10(bufferMemory-1));
		int cost = (int)Math.ceil(2*(leftTable+rightTable)*(1+multiplier) + leftTable + rightTable);
		if(obj.joinCost>cost){
			obj.joinCost=cost;
			obj.joinType = "Hash Join buffer = " + bufferMemory ;
		}
		return cost;
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}
	
        
        //But for less buffer memory = 30
	//Total Cost: 2 * (M+N) * (1+logB-1(M+N)/B-1) + M + N
	public static int hashJoinCostWithLessBuffer(int leftTable, int rightTable, SQLProcess obj){
	try
        {
            int bufferMemory = bufferMemoryWithlessBuff;
            double multiplier = Math.log10((leftTable+rightTable)/(bufferMemory-1))/(Math.log10(bufferMemory-1));
            int cost = (int)Math.ceil(2*(leftTable+rightTable)*(1+multiplier) + leftTable + rightTable);
            if(obj.joinCost>cost){
		obj.joinCost=cost;
		obj.joinType = "Hash Join buffer = " + bufferMemory;
            }
            return cost;
        }
        catch(Exception ex)
        {
            
        }
        return 0;
	}
}

//select process 
class SQLProcess
{
    long joinCost=0;
    boolean isReverseOrder;
    String joinType;
}