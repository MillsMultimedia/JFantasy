class Thief extends Character
{ 
    private int FI_HP = 60;
    private int FI_AP = 20;
    private int FI_SP = 0;
    private int FI_MP = 0;
    
    public Thief ()
    {
        super(15, 8, 0, 0);
    }
    
    public String getName ()
    {
        return "THIEF";
    }
    
}