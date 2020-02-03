/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: STUDENT(S) NAME
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**A class that opens a file as an object input stream
 * and prints the contents inside to the console window
 * @author Riley Berry and Zachary Graham
 *
 */
public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several reords.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while ( true )
            {
                record = (MusicRecord) input.readObject();
                System.out.println(record);
//                System.out.println((MusicRecord) input.readObject());
                // TO BE COMPLETED BY THE STUDENTS
                
           
            }   // END OF WHILE
        }catch(EOFException e) {
        	System.out.println("End of file");
        }
        catch(ClassNotFoundException | IOException e)
        {
        	                // ADD NECESSARY catch CLAUSES HERE
        }
        try {
        	input.close();
        	
        }catch(IOException e) {
        	e.printStackTrace();
        }
//
    }           // END OF METHOD 
    
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("mySongs.ser");
    }
}