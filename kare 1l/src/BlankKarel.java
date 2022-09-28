import stanford.karel.*;

public class BlankKarel extends SuperKarel {
	public int map_size;
	public int section_size;
	int steps;

	private void findMap() {
		int count = 0;
		for (; ; ) {
			if (frontIsBlocked()) {
				break;
			}
			count++;
			myMove();
		}
		map_size = count + 1;
		if (map_size % 2 == 0) {
			section_size = map_size / 2 - 1;
		}
		else {
			section_size = map_size / 2;
		}
	}
	private void specialPutBeeper(){
		if(!beepersPresent())
			putBeeper();
	}
	private void drawLine() {
		if(map_size %2==0){
			for (int i = 1; i < map_size / 2; i++) {
				myMove();
				specialPutBeeper();
			}
		}
		else{
			for (int i = 0; i < map_size / 2; i++) {
				myMove();
				specialPutBeeper();
			}
		}
	}
	private void myMove() {
		move();
		steps++;
	}
	private void toHome() {
		while (notFacingSouth()) {
			turnLeft();
		}
		while (!frontIsBlocked()){
			myMove();
		}
		while (notFacingWest()) {
			turnLeft();
		}
		while (!frontIsBlocked()) {
			myMove();
		}
	}
	private void turnFace(char a){
		if ( a == 'w')
			while (notFacingWest()) {
				turnLeft();
			}
		if ( a == 'e')
			while (notFacingEast()) {
				turnLeft();
			}
		if ( a == 'n')
			while (notFacingNorth()) {
				turnLeft();
			}
		if ( a == 's')
			while (notFacingSouth()) {
				turnLeft();
			}
	}
	private void drawSquare(char a, char b, char c, char d) {

		char arr[] = {a, b, c, d};
		for (int i =0; i <4; i++) {
			turnFace(arr[i]);
			specialPutBeeper();
			if(i!=3)//if not finished from drawing the center
			{
				myMove();
			}
		}

	}
	private void goToBesideCenter() {
		int a = 0;
		if (section_size % 2 == 0) {
			a = 1;
		}
		for (int i = a; i < section_size / 2; i++) {
			specialPutBeeper();
			myMove();
		}
		specialPutBeeper();
	}
	private void goToCenter(int b ){
		int a = -1 ;
		if (section_size % 2 == 0) {
			a = 0;
		}
		for (int i = a; i < section_size / 2; i++) {
			if(b==1)
				specialPutBeeper();
			myMove();
		}
		if(b==1)
			specialPutBeeper();
	}
	private void solve(char destination1, char destination2 , char destination3, char destination4, char destination5 , int section) {
		goToBesideCenter();
		char arr [] = {destination1, destination2, destination3, destination4,destination5};
		for (int i =0; i <arr.length; i++) {
			turnFace(arr[i]);
			if (i == 0) {// draw square

				goToCenter(0);
				if (this.section_size % 2 == 0){
					if(section ==1)
						drawSquare('e', 'n', 'w', 'w');
					else if(section ==2)
						drawSquare('n','w' , 's' ,'s');
					else if(section ==3)
						drawSquare('w','s', 'e' ,'e');
					else
						drawSquare('s','e', 'n' ,'n');
				}
				else {
					specialPutBeeper();
					turnAround();
				}
				if(section ==4 && map_size %2==1) {
					 toHome();
					break;
				}
				goToCenter(0);

			}
			if (i == 1)//if go to map center
			{
				goToCenter(1);
			}
			if (i == 2) {//if go draw line
				drawLine();
			}
			if(section ==4 && i==3){// if finished
			toHome();
			break;
			}
			else { //if not finished
				if(map_size %2==0)
				if (i == 3) {
					myMove();
				}
			}
		}
	}
	private void goToTheFistPoint(){
		int a = 0 ;
		if(map_size %2==0) {
			a=1;
		}
		turnAround();
		for (int i = a; i < map_size / 2; i++) {
			myMove();
		}
		while (notFacingNorth()){
			turnLeft();
		}
	}


	public void run() {
		steps = 0;
		findMap();
		goToTheFistPoint();
		solve('e','n','e','n','w' ,1);
		solve('n','w','n','w','s', 2);
		solve('w','s','w','s','e', 3);
		solve('s','e','s','e','n', 4);
		System.out.println("map size = " + map_size + "*" + map_size);
		System.out.println("steps = " + steps + "\n");
	}
}
