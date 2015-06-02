class Wizard extends Character
{ 
    private int FI_HP = 60;
    private int FI_AP = 20;
    private int FI_SP = 0;
    private int FI_MP = 0;
    
    public Wizard ()
    {
        super(10, 5, 25, 20);
    }
    
    public String getName ()
    {
        return "WIZARD";
    }
    
}