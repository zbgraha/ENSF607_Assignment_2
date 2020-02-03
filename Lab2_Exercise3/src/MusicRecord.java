import java.io.Serializable;


/**A simple class that represents a music record. Contains the year
 * of recording, song same, singer name, and purchase price
 * @author Riley Berry and Zachary Graham
 *
 */
class MusicRecord implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private int year_recorded;
		private String songName;
		private String singerName;
		private double purchase_price;
		
        /**
         * A default constructor that builds a record with blank data
         */
		public MusicRecord() {
			this( 0, "", "", 0.0 ); 
		} 
		
		public MusicRecord(MusicRecord old) {
			setYear( old.getYear());
			setSongName( old.getSongName());
			setSingerName( old.getSingerName());
			setPrice( old.getPurchasePrice() );
		}
	  
        /**
         * A constructor that initializes the music records with supplied 
         * data.
         */
		public MusicRecord( int year, String song, String singer, double value ) {
			setYear( year );
			setSongName( song );
			setSingerName( singer );
			setPrice( value );
		} 
		
        
        /**
         * Sets the data field year_recorded to supplied argument year
         * data.
         */
		public void setYear( int year ) {
			year_recorded = year;
		}
        
        
        /**
         * Returns the recording year
         */
		public int getYear() {
			return year_recorded; 
		}
        
        /**
         * Sets the data field songName to supplied argument song
         */
		public void setSongName( String song ) {
			songName = song;
		}
        
        
        /**
         * Returns the songName name
         */
		public String getSongName()  {
			return songName; 
		} 
		
        /**
         * Sets the data field sinterName to supplied argument singer
         */
		public void setSingerName( String singer ) {
			singerName = singer;
		}
        
        /**
         * Returns the singer's name
         */
		public String getSingerName() {
			return singerName; 
		}
        
        /**
         * Sets the data field purchase_price to supplied argument price
         */
		public void setPrice( double value ) {
			purchase_price = value;
		}
        
        /**
         * Returns the price
         */
		public double getPurchasePrice(){
			return purchase_price; 
		} 
		
		public String toString() {
			String st = "";
			st+= getYear() + "\n"+ getSongName()+ "\n"+ getSingerName() + "\n"+ getPurchasePrice();
			st+="\n-------------------";
			return st;
		}
	} 
