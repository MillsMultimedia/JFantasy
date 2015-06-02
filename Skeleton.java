class Skeleton extends Character
{
    //final ints setting skeleton's hp/ap/sp
        //increase or decrease to change game difficulty
    private final int SKEL_HP = 25;
    private final int SKEL_AP = 10;
    private final int SKEL_SP = 0;
    private final int SKEL_MP = 0;
    
    public Skeleton ()
    {
        super( 25, 5, 0, 0); //calls Monster class with skeleton settings
    }
    
    
    public String getName ()
    {
        return "SKELETON";
    }
    
}