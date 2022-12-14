package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

/**
 * Represents a time period with a start and end time.
 *
 */
public enum TimeOfDay {

	MANHA (8, 0, 12, 59),
	TARDE (13, 0, 20, 59);




	private int startHour;        
	private int startMinute;
	private int endHour;
	private int endMinute;



	/**
	 * @return the startHour
	 */
	 public int getStartHour() {
		 return startHour;
	 }


	 /**
	  * @return the startMinute
	  */
	 public int getStartMinute() {
		 return startMinute;
	 }

	 /**
	  * @return the endHour
	  */
	 public int getEndHour() {
		 return endHour;
	 }

	 /**
	  * @return the endMinute
	  */
	 public int getEndMinute() {
		 return endMinute;
	 }


	 /**
	  * Constructs a new TimeOfDay object with the specified start and end time.
	  *
	  * @param startHour the hour of the start time
	  * @param startMinute the minute of the start time
	  * @param endHour the hour of the end time
	  * @param endMinute the minute of the end time
	  */
	 TimeOfDay (int startHour, int startMinute, int endHour, int endMinute) {
		 this.startHour = startHour;
		 this.startMinute = startMinute;
		 this.endHour = endHour;
		 this.endMinute = endMinute;
	 }
}

