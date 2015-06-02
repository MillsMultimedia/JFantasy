class Priest extends Character
{ 
    //change these stats for stronger or weaker character
    int PR_HP = 60;
    int PR_AP = 15;
    int PR_SP = 25;
    int PR_MP = 0;
    
    public Priest ()
    {
        super(60, 15, 25, 0);
    }
    
    public String getName ()
    {
        return "PRIEST";
    }
    
}