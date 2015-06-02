class Knight extends Character
{ 
    //change these stats for stronger or weaker character
    int FI_HP = 60;
    int FI_AP = 20;
    int FI_SP = 0;
    int FI_MP = 0;
    
    public Knight ()
    {
        super(60, 20, 0, 0);
    }
    
    public String getName ()
    {
        return "KNIGHT";
    }
    
}