public class ClientProtocol {
    
    Ia processor;
    String theWorld
    boolean isSetedUp;
    
    public ClientProtocol() {
        isSetedUp = false;
    }
    
    public void setup() {
        String command;
        
        System.out.println("You just started the Hunt the Wumpus client\n"
                          +"you may now configure the game\n"
                          +"type \"default\" if you want the default settings\n");
        
        theWorld = stdIn.readLine();
        
        System.out.println("you may now configure an IA to play\n"
                          +"\n"
                          +"If you just will to play for yourself enter \"play\"\n"
                          +"otherwise enter the number of the IA to use\"IA\""
                          +"1- simple IA with random moves\n"
                          +"2- simple IA with random moves and learning to an extent\n"
                          +"3- robust IA with calculated moves based on learning\n");
                          
        command = stdIn.readLine();
        if (command.equals("")||command.equals("play")) return;

        try {
			processor = (Ia) Class.forName("IA.IA"+command).getConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return;
    }
    
    public String processOrPlay(String fromServer) {
        if(isSetedUp == false) {
            isSetedUp = true;
            return theWorld;
        }
        if(processor==null){
            return stdIn.readLine();
        }
        
        ArrayList<String> info = new ArrayList<String>();
                
        try {
        	Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
      	}
            	
        String[] temp = fromServer.split("\\s+");
        for (int i = 0; i<temp.length; i++) {
            info.add(temp[i]);
        }
        
        try {
            int x = Integer.valueOf(info.get(0));
            int y = Integer.valueOf(info.get(1));
            if ( x > -1 && y > -1){
                return = processor.jouer(x, y, info)+"\n";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        return = "\t\n";
    }
}