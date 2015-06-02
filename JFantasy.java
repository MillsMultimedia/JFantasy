/*******************************************************************
* CSCIE10B Graduate Project
*
* JFantasy is a partial clone of Final Fantasy for the NES
* it takes you through a single battle like the original
* all the icons are created by the program author and are
* based loosely on the original game
*
* @author: Jeffrey Mills
********************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



class JFantasy extends JFrame implements MouseListener
{
    //set all my private variables
    private JPanel screenText = new JPanel();
    private JPanel playerFrame = new JPanel();
    private JPanel enemyFrame = new JPanel();
    private Font font = new Font("Helvetica", Font.BOLD, 25);
    private JLabel f = new JLabel();
    private JLabel choice1 = new JLabel();
    private JLabel choice2 = new JLabel();
    private JLabel choice3 = new JLabel();
    private JLabel actionText = new JLabel();
    private JLabel actionTaker = new JLabel();
    private int currentPlayer = 0;
    private Character [] players;
    private JLabel [] icons = new JLabel[4];
    private Character [] monsters;

    public static void main (String [] args)
    {
    
        JFantasy newGame = new JFantasy();
        newGame.buildScreen();    
        newGame.showChoices();

     } // main
     
/**
    * initPlayers() creates an array of Characters
    * one of each player type
    */   
    private Character [] initPlayers()
    {
        Character [] players = new Character[4]; //initialize Player Characters
            players[0] = new Knight();
            players[1] = new Thief();
            players[2] = new Wizard();
            players[3] = new Priest();
            
        icons[0] = new JLabel();
        icons[1] = new JLabel();
        icons[2] = new JLabel();
        icons[3] = new JLabel();
            
        return players;
    }

/**
    * initMonsters creates an array of Skeletons
    * 
    */   
    private Character [] initMonsters()
    {
        Character [] monsters = new Skeleton[6];
        
        for (int i = 0; i < monsters.length; i++)
            {
                monsters[i] = new Skeleton();
                monsters[i].setAction('f');
                System.out.println("Monster: " + monsters[i].getName() + "-" + i);
                System.out.println("Health: " + monsters[i].getHP());
            }
            
        return monsters;

    }

/**
    * setPlayerIcons & setMonsterIcons create JLabels for each
    * player character and monster character and sets their
    * ImageIcon to a jpg
    *
    *@param each accepts an array for either the players (p) or monsters (m)
    */
    private void setPlayerIcons (Character [] p)
    {
        playerFrame.removeAll();
        
        
        if ( checkDead(p) )
        {
            try
            {
            choice1.setText("Game Over. You lose.");
            Thread.sleep(10000);
            System.exit(0);
            }
            catch (InterruptedException e) { }
        }
        else
        {  
            for (int i = 0; i < 4; i++)
            {
                char n = p[i].getName().charAt(0);
                icons[i].setText(p[i].getName());
                
                if (p[i].getHP() > 0)
                    icons[i].setIcon( new ImageIcon("images/" + n + "1.jpg"));
                else
                    icons[i].setIcon( new ImageIcon("images/dead.jpg"));
                    
                icons[i].addMouseListener(this);
                playerFrame.add(icons[i]);
            }
        }
        

        
        
    }
    
    private void setMonsterIcons (Character [] m)
    {
        enemyFrame.removeAll();
        
        if ( checkDead(m) )
        {
            try
            {
            choice1.setText("Game Over. You win!");
            Thread.sleep(10000);
            System.exit(0);
            }
            catch (InterruptedException e) { }
        }
        else
        {
            for (Character mo : monsters) //check monster init
                {
                    mo.setAction('F');
                    do
                    {
                        int rand = (int)(Math.random()*3);
                        mo.setVictim( rand );
                    } while ( players[ mo.getVic() ].getHP() <= 0 );
                }
            
            for (int i = 0; i < m.length; i++)
            {
                String name = m[i].getName() + "-" + i;
                JLabel mIcon = new JLabel( name );
            
                if ( m[i].getHP() > 0 )
                {
                    mIcon.setIcon( new ImageIcon("images/skeleton.jpg") );
                    mIcon.addMouseListener (this);
                }
                else
                {
                    mIcon.setPreferredSize( new Dimension(131, 131) );
                    mIcon.setBackground(Color.BLACK);
                }
            
                enemyFrame.add( mIcon );
            }
        }
    }

/**
    * animateFight cycles through images for players
    * simulating an attack animation
    *
    * @param p - the character attacking
    */    
    private void animateFight (Character p)
    {
        char n = p.getName().charAt(0);
        for (int repetition = 0; repetition < 3; repetition++)
          {
            try
            {
               icons[currentPlayer].setIcon( new ImageIcon("images/" + n + "2.jpg") );
               Thread.sleep(400);
               
               if (p.getAction() == 'H' || p.getAction() == 'R')
                    icons[currentPlayer].setIcon( new ImageIcon("images/" + n + "4.jpg") );
               else
                    icons[currentPlayer].setIcon( new ImageIcon("images/" + n + "3.jpg") );
               Thread.sleep(250);
               
            }
            catch (InterruptedException e) { }
          }
        icons[currentPlayer].setIcon( new ImageIcon("images/" + n + "1.jpg") );
    }

/**
    * fightRound cycles through the players then the monsters
    * and calls each character's action before calling the method
    * to redraw the icons
    *
    */   
    private void fightRound ()
    {
        currentPlayer = 0;
        actionTaker.removeAll(); 
        actionTaker.setText("fight!");
        choice1.removeAll(); ;
        choice2.removeAll(); ;
        
        for (int p = 0; p < players.length; p++)
        {
            char action = players[p].getAction();
            
            if (players[p].getHP() > 0);
            {            
            if (action == 'F')
                melee(players[p], monsters[ players[p].getVic() ]);
            else if (action == 'R')
                heal(players[p], players[ players[p].getVic() ]);
            else if (action == 'H')
                cast(players[p], monsters[ players[p].getVic() ]);
            }
        }
        
        for (int m = 0; m < monsters.length; m++)
        {
            if (monsters[m].getHP() > 0)
                melee(monsters[m], players[ monsters[m].getVic() ]);
        }
        
        setPlayerIcons(players);
        setMonsterIcons(monsters);
        showChoices();
    }
    
/**
    * melee performs the action for the FIGHT selection
    * 
    * @param attacker - the character performing the action
    * @param defender - the recipient of the attack
    */
    private void melee (Character attacker, Character defender)
    {
        int dmg = attacker.attackPower();
        char pl = players[currentPlayer].getName().charAt(0);
        actionText.removeAll(); 
        
        defender.setHP( defender.getHP() - dmg );
        
        if (!attacker.getName().equals("SKELETON"))
        {
            System.out.println("animate called");
            animateFight(attacker); 
        }
                        
        try
        {
           if (defender.getHP() <= 0)
           {
                System.out.println( attacker.getName() + " attacks " + defender.getName() +
                            " for " + dmg + " damage.  " + defender.getName() +
                            " is defeated.");
                actionText.setText( attacker.getName() + " attacks " + defender.getName() +
                            " for " + dmg + " damage.  " + defender.getName() +
                            " is defeated.");
            }
            else
            {
                System.out.println( attacker.getName() + " attacks " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " has " + ( defender.getHP() ) + " remaining.");
                actionText.setText( attacker.getName() + " attacks " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " has " + ( defender.getHP() ) + " remaining.");
            }
            
           Thread.sleep (500);
        }
        catch (InterruptedException e) { }
        
        
        
        
    } // fight

/**
    * cast performs the action for the CAST>HELLFIRE selection
    * 
    * @param caster - the character performing the action
    * @param defender - the recipient of the attack
    */  
    private void cast (Character caster, Character defender)
    {
        int dmg = caster.spellPower();
        
            defender.setHP( defender.getHP() - dmg );
        
        if (!caster.getName().equals("SKELETON"))
        {
            System.out.println("animate called");
            animateFight(caster); 
        }    
        try
        {
            if (defender.getHP() <= 0)
            {
                System.out.println( caster.getName() + " casts [SPELL] " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " is defeated.");
                actionText.setText( caster.getName() + " casts [SPELL] " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " is defeated.");
            }
            else
            {
                System.out.println( caster.getName() + " casts [SPELL] " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " has " + ( defender.getHP() ) + " remaining.");
                actionText.setText( caster.getName() + " casts [SPELL] " + defender.getName() +
                                " for " + dmg + " damage.  " + defender.getName() +
                                " has " + ( defender.getHP() ) + " remaining.");
            }
            Thread.sleep (500);
        }
        catch (InterruptedException e) { }
            
    } // cast

/**
    * melee performs the action for the CAST>REVIVE selection
    * 
    * @param caster - the character performing the action
    * @param defender - the recipient of the heal
    */
    private void heal (Character caster, Character defender)
    {
        int amt = caster.spellPower();
        
        if (defender.getHP() <= 0)
            defender.setHP( 1 );
        else
            defender.setHP( defender.getHP() + amt );
        
        try
        {
            System.out.println( caster.getName() + " casts HEAL " + defender.getName() +
                                    " for " + amt + " points.  " + defender.getName() +
                                    " has " + ( defender.getHP() ) + " remaining.");
            actionText.setText( caster.getName() + " casts HEAL " + defender.getName() +
                                    " for " + amt + " points.  " + defender.getName() +
                                    " has " + ( defender.getHP() ) + " remaining.");
            Thread.sleep(500);
        }
        catch (InterruptedException e) { }
            
    } // heal
    
/**
    * showChoices displays the default selection in
    * choice1 and choice2 labels
    * 
    */
    private void showChoices()
    {
        choice1.setForeground(Color.WHITE);
        choice2.setForeground(Color.WHITE);
        choice3.setForeground(Color.WHITE);
        actionTaker.setForeground(Color.WHITE);
        
        choice1.removeAll();
        choice1.removeMouseListener(this);
        choice2.removeAll();
        choice2.removeMouseListener(this);
        actionText.removeAll(); 
        actionTaker.setText( players[currentPlayer].getName() + ":" );
        actionTaker.setFont( new Font("Helvetica", Font.BOLD, 25) );
        actionTaker.setPreferredSize( new Dimension(396, 25) ); 
        screenText.add(actionTaker);
        
        choice1.setText("Fight"); 
        choice1.addMouseListener(this);
        choice1.setFont(font);
        choice1.setPreferredSize( new Dimension(396, 40) ); 
        screenText.add(choice1);
        
        choice2.setText("Cast");
        choice2.addMouseListener(this);
        choice2.setFont(font);
        choice2.setPreferredSize( new Dimension(396, 40) ); 
        screenText.add(choice2);

        screenText.add(choice3);
    }
    
/**
    * showSpells displays the spell selection
    * in choice1 and clears choice2 labels
    * 
    */
    private void showSpells()
    {
        if (currentPlayer == 2)
        {
            choice1.setText("Hellfire");
        }
        else if (currentPlayer == 3)
            choice1.setText("Revive");
        else
        {
            choice1.setText( "No spells");
            System.out.println( "CP " + currentPlayer);   
        }
            
        choice2.setText("");
    }

/**
    * setSpell sets the action for the current player
    * to cast the chosen spell
    * 
    */    
    private void setSpell()
    {
        if (currentPlayer == 2)
        {
            players[currentPlayer].setAction('H');
            choice1.setText("Target select");
            choice1.removeMouseListener(this);
            choice2.setText("");
        }
        else if (currentPlayer == 3)
            showHealee();
        else
            choice1.setText( "No spells for " + players[currentPlayer].getName() );
    }

/**
    * showHealee displays the choose target selection in
    * choice1 label and clears the choice2 label
    * 
    */    
    private void showHealee ()
    {
        choice1.setText("Target select");
        choice1.removeMouseListener(this);
        choice2.setText("");
    }

 /**
    * sets the recipient of the heal spell
    * 
    */     
    private void setHealee(int i)
    {
        players[currentPlayer].setAction('R');
        players[currentPlayer].setVictim(i);
        incrementPlayer();
            if (currentPlayer > 3)
            {
                currentPlayer = 0;
                fightRound();
            }
    }
 
 /**
    * showHealee displays the choose target selection in
    * choice1 label and clears the choice2 label
    * 
    */   
    private void setMove()
    {
        players[currentPlayer].setAction('F');
        choice1.removeMouseListener(this);
        choice1.setText("Target select");
        choice2.setText("");
    }

 /**
    * sets the recipient of an attack
    * 
    */     
    private void setTarget(String s)
    {
       String num = s.substring( s.length() - 1 );
       System.out.println("Substring: " + num);
       players[ currentPlayer ].setVictim( Integer.parseInt( num ));
        
       System.out.println( players[currentPlayer].getVic() );
       
       incrementPlayer();
       
       showChoices();

    }
    
    private void incrementPlayer()
    {
        if (currentPlayer < 3)
        {
            System.out.println("into increment < 3  CP = " + currentPlayer);
            currentPlayer++;
            System.out.println("after++  CP = " + currentPlayer);
            
            if (players[currentPlayer].getHP() <= 0)
                incrementPlayer();
        }
        else
            fightRound();

    }
/**
    *checkDead() verifies that at least one character in the array
    * is still alive so the game can continue
    *
    *@param ca - Character Array
    */
    private boolean checkDead( Character [] ca )
    {
        for (int i = 0; i < ca.length; i++)
        {
            if (ca[i].getHP() > 0)
                return false;
        }
        
        return true;
    }
    
/**
    * Layout the basic game area
    * 3 main sections for Monsters, Player Characters and Messages
    */
    private void buildScreen()
    {
        //Set up the background
        JFrame frame = new JFrame();
            frame.setSize(725, 700);
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            
        JPanel container = new JPanel();
            container.setLayout ( new FlowLayout() );
            container.setPreferredSize(new Dimension(725, 700));
            container.setFont(font);
            setForeground(Color.WHITE);
            container.setBackground(Color.BLACK);
            frame.add(container);
            
        JPanel innerLeft = new JPanel();
            innerLeft.setPreferredSize(new Dimension(450, 545));
            innerLeft.setBackground(Color.BLACK);
            container.add(innerLeft);
            
        JPanel innerRight = new JPanel();
            innerRight.setPreferredSize(new Dimension(195, 545));
            innerRight.setBackground(Color.BLACK);
            container.add(innerRight);           
            
        enemyFrame.setPreferredSize(new Dimension(450, 420));
        enemyFrame.setBackground(Color.BLACK);
        enemyFrame.setLayout ( new FlowLayout() );
        enemyFrame.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 4, Color.WHITE)); //Found solution at http://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
        innerLeft.add(enemyFrame);
            
        playerFrame.setPreferredSize(new Dimension(195, 420));
        playerFrame.setBackground(Color.BLACK);
        playerFrame.setLayout( new FlowLayout() );
        playerFrame.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.WHITE)); //Found solution at http://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
        innerRight.add(playerFrame);
        
        //helps line up player and enemy frames
        JPanel spacer = new JPanel();
            spacer.setPreferredSize(new Dimension(195, 125));
            spacer.setBackground(Color.BLACK);
            innerRight.add(spacer);
            
        screenText.setForeground(Color.RED);
        screenText.setPreferredSize(new Dimension(395, 125));
        screenText.setFont(font);
        screenText.setBackground(Color.BLACK);
        //screenText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE)); //Found solution at http://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
        innerLeft.add(screenText);
        
        actionText.setPreferredSize(new Dimension(700, 50));
        actionText.setFont( new Font("Helvetica", Font.BOLD, 12) );
        actionText.setForeground(Color.WHITE);
        actionText.setBackground(Color.BLACK);
        container.add(actionText);
        
        frame.setVisible(true);
        
        players = initPlayers();
        setPlayerIcons(players);
        monsters = initMonsters();
        setMonsterIcons(monsters);
    }
    
 
  
/**
    * mouse listener
    * uses the first char in the label clicked to
    * determine the next method call
    * 
    */   
    public void mouseClicked(MouseEvent evt) {
        JLabel j = (JLabel) evt.getSource();
        String text = j.getText();
        
        choice3.setText( text );
        
        switch (text.charAt(0))
        {
            case 'F':
                setMove();
                break;
            case 'C':
                setSpell();
                break;
            // case 'H': //use Hellfire for spell
//                 setSpell(text);
//                 break;
//             case 'R': //use Revive as heal
//                 setSpell(text);
//                 break;
            case 'S':
                setTarget(text);
                break;
            case 'N':
                showChoices();
                break;
            case 'K': 
                setHealee(0);
                break;
            case 'T': 
                setHealee(1);
                break;
            case 'W': 
                setHealee(2);
                break;
            case 'P':
                setHealee(3);
                break;
            default:
                break;
        }
    
    }
    
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}

} // JFantasy