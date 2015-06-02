import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


abstract class Character implements MouseListener
{
    // CHARACTER STATS VARIABLES
    private int hp; // hit points
    private int maxHP; // max hit points
    private int ap; // attack power
    private int sp; // spell power
    private int mp; // magic points
    
    // CHARACTER ACTION VARIABLES
    private char act = ' ';
    private int vic = 0;
    private boolean que = false;
    
    public Character ()
    {
        //no constructor zeroes out character stats
        hp = 0; ap = 0; sp = 0; mp = 0; 
    }
    
    public Character (int h, int a, int s, int m)
    {
        //temporary constructor until I can get call super to work in sub class
        hp = h; ap = a; sp = s; mp = m; 
        maxHP = h;
    }
    
/**
        * getters and setters for various stats
        *
        */ 
    public int attackPower () //get ap
    {
        return ap;
    }
    
    public int spellPower () //get sp
    {
        return sp;
    }
    
    public int getHP() //get hit points
    {
        return hp;
    }

    public int setHP(int setter) //change hitpoints
    {
        if (setter > maxHP)
            hp = maxHP;
        else
            hp = setter;
        return hp;
    }
    
    public int getMP() //get magic points NOTE: not used in current JFantasy version 1.x
    {
        return mp;
    }

    public int setMP(int setter) //change magic points NOTE: not used in current JFantasy version 1.x
    {
        mp = setter;
        return mp;
    }
    
    public void setAction(char selection) //stores the current action selection
    {
        act = selection;
    }
     
    public char getAction() //get the current action selection
    {
        return act;
    }
    
    public void setVictim(int victim) //set the action recipient
    {
        vic = victim;
    }
    
    public int getVic () //get the action recipeint
    {
        return vic;
    }
    
//     public void setQueue (boolean b)
//     {
//         que = b;
//     }
//     
//     public boolean isQueued ()
//     {
//         return que;
//     }
    
    abstract String getName(); 
    
    public void mousePressed(MouseEvent evt) {
        JLabel j = (JLabel) evt.getSource();
        
        String target = j.getText().substring(0, 1);
        int n = Integer.parseInt( target );
        setVictim( n ); 
    }
    
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}