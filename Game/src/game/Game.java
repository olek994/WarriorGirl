/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.net.URL;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 *
 * @author Aleksander
 */
public class Game extends Application {
    
    private Scene scene;
    private Label startGameLabel;
    private Label titleLabel;
    private Label gameOverLabel;
    private Label lifeLabel;
    private Label descriptionLabel;
    private Label winLabel;
    private Label restartLabel;
    private Label keepGoingLabel;
    private double opacity = 1;
    private double winLabelOpacity = 0;
    private double  moveVariable = 0;
    private Integer numberOfEnemys = 9;
    private Boolean runing = false;
    private Boolean jump = false;
    private Boolean jumpOver = false;
    private Boolean shoot = false;
    private Boolean gameOver = false;
    
    private Boolean toAir = false;
    private Boolean inAir = false;
    private Boolean onGround = false;
    
    private Character character;
    private Pane root;
    private Enemy[] enemys = new Enemy[numberOfEnemys];
    private Boolean start = false;
    private Integer road = 1;
    private Image heart;
    private ImageView[] life;
    private ImageView backgroundView;
    private Integer health = 5;
    private Boolean moving = false;
    private AudioClip shoutClip;
    private AudioClip shoutCHClip;
    private AnimationTimer backgroundMoving;
    private AudioClip winClip;
    private AudioClip inGameMusicClip;
    private AudioClip loseClip;
    private AnimationTimer winAnimation;
    private AnimationTimer restartLabelAnimation;
    private AnimationTimer keepGoingAnimation;
    private AnimationTimer jumpOverAnimation;
    private int[] roadNumber = {-40,0,40};  
    private Random r = new Random();
    /**
     * Creating and editing all features in game
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        
        //Label
        startGameLabel = new Label("Press spacebar to start...");
        startGameLabel.setId("startlabel");
        startGameLabel.setTranslateX(450);
        startGameLabel.setTranslateY(470);
        
        titleLabel = new Label("Warrior Girl");
        titleLabel.setId("titlelabel");
        titleLabel.setTranslateX(450);
        titleLabel.setTranslateY(30);
         
        gameOverLabel = new Label("Game Over");
        gameOverLabel.setId("gameoverlabel");
        gameOverLabel.setTranslateX(400);
        gameOverLabel.setTranslateY(200);
        
        lifeLabel = new Label("Your Lives");
        lifeLabel.setId("lifelabel");
        lifeLabel.setTranslateX(450);
        lifeLabel.setTranslateY(50);
        
        descriptionLabel = new Label("Push forward and Watch out for the zombies. Don't let them touch you\nRun to save your kingdom and win\nArrows - moving\nW - missiles");
        descriptionLabel.setId("descriptionlabel");
        descriptionLabel.setTranslateX(330);
        descriptionLabel.setTranslateY(150);
        
        winLabel = new Label("Congratulations!! You saved kingdom");
        winLabel.setId("winlabel");
        winLabel.setTranslateX(100);
        winLabel.setTranslateY(200);
        winLabel.setOpacity(0);
        winAnimation = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
               winLabel.setOpacity(winLabelOpacity);
               winLabelOpacity += 0.01;
               
            }
            
        };
        restartLabel = new Label("Press Spacebar to restart...");
        restartLabel.setId("startlabel");
        restartLabel.setTranslateX(450);
        restartLabel.setTranslateY(470);
        restartLabelAnimation = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
               restartLabel.opacityProperty().set(opacity);
               opacity -= 0.01;
               if(opacity < 0.01)
               {
                   opacity = 1;
               }
            }
            
        };
        
        keepGoingLabel = new Label("Keep Going");
        keepGoingLabel.setId("keepgoinglabel");
        keepGoingLabel.setTranslateX(900);
        keepGoingLabel.setTranslateY(50);
        keepGoingAnimation = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                keepGoingLabel.setOpacity(opacity);
                opacity -= 0.01;
                if(opacity < 0.01)
               {
                   opacity = 1;
               }
            }
            
        };
        
        new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                
               startGameLabel.opacityProperty().set(opacity);
               opacity -= 0.01;
               if(opacity < 0.01)
               {
                   opacity = 1;
               }
            }
            
        }.start();
        
        
        //Audio
        URL shootLink = getClass().getResource("shoot.wav");
        AudioClip shootClip = new AudioClip(shootLink.toString());
        
        URL shoutLink = getClass().getResource("shoutEnemy.wav");
        shoutClip = new AudioClip(shoutLink.toString());
        
        URL backMusic = getClass().getResource("backgroundMusic.wav");
        AudioClip backClip = new AudioClip(backMusic.toString());
        backClip.play(1);
        
        URL inGamelink = getClass().getResource("inGameMusic.wav");
        inGameMusicClip = new AudioClip(inGamelink.toString());
        
        URL shoutCHLink = getClass().getResource("shoutCharacter.wav");
        shoutCHClip = new AudioClip(shoutCHLink.toString());
        
        URL winLink = getClass().getResource("winner.wav");
        winClip = new AudioClip(winLink.toString());
        
        URL loseLink = getClass().getResource("lose.wav");
        loseClip = new AudioClip(loseLink.toString());
        
        //Background image
        Image background = new Image(getClass().getResourceAsStream("background.jpg"));
        
       //Background ImageView
        backgroundView = new ImageView(background);
        backgroundView.setFitWidth(3000);
        backgroundView.setFitHeight(550);
        backgroundMoving = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
              
              backgroundView.setLayoutX(moveVariable);
              moveVariable -= 2;
              if(moveVariable == -1750)
              {
                  stopGame();
                  winClip.play(1);
                  winAnimation.start();
                  for(int i = 0;i<enemys.length;i++)
                      root.getChildren().remove(enemys[i]);
                  root.getChildren().add(winLabel);
              }
                
            }
            
        };
        
                
        //character
        character = new Character();
        character.setTranslateX(0);
        character.setTranslateY(420);
        AnimationTimer characterHit = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
               for(int i = 0;i<enemys.length;i++)
               {
//                   
                   if(colideEnemyWall(enemys[i]))
                   {
                       root.getChildren().remove(life[health-1]);
                       health--;
                       if(health == 0){
                           root.getChildren().add(gameOverLabel);
                           loseClip.play(1);
                           stopGame();
                       }
                       enemys[i].setTranslateX(1500 + r.nextInt(500 - 300)+300);
                       enemys[i].setTranslateY(420 + roadNumber[r.nextInt(3)]);
                       enemys[i].Health = 2;
                   }
                   if(colideCharacterEnemy(character, enemys[i]))
                   {
                       shoutCHClip.play(0.5);
                       root.getChildren().remove(life[health-1]);
                       health--;
                       if(health == 0){
                           root.getChildren().add(gameOverLabel);
                           loseClip.play(1);
                           stopGame();
                       }
                       enemys[i].setTranslateX(1500 + r.nextInt(500 - 300)+300);
                       enemys[i].setTranslateY(420 + roadNumber[r.nextInt(3)]);
                       enemys[i].Health = 2;
                       
                       
                   }
                   
                      
               }
            }
            
        };
        
        
        
        //Heart Image
        heart = new Image(getClass().getResourceAsStream("heart.png"));
        //Character life
        life = new ImageView[health];
        for(int j =0;j<life.length;j++)
        {
            life[j] = new ImageView(heart);
            life[j].setFitHeight(40);
            life[j].setFitWidth(40);
            life[j].setTranslateX(450 + (45*j));
            life[j].setTranslateY(100);
        }
        
        
        //Enemys
        
        
        for(int i =0;i<enemys.length;i++)
        {
            enemys[i] = new Enemy();
            enemys[i].setScaleX(-1);
            enemys[i].setTranslateY(420 + roadNumber[r.nextInt(3)]);
            enemys[i].setTranslateX(800 + (r.nextInt(1000 - 400) + 400));
            
        }
        
        //rootPane
        root = new Pane();
        root.getChildren().addAll(backgroundView,startGameLabel,titleLabel,descriptionLabel);
        //Scene
        
        scene = new Scene(root, 1200, 550);
        scene.getStylesheets().add("game/Style.css");
        
        scene.setOnKeyPressed((KeyEvent k) -> {
         if(!gameOver)
         {
            switch(k.getCode())
            {
                case SPACE:
                    if(!start)
                    {
                    start = true;
                    
                    backgroundMoving.stop();
                    root.getChildren().removeAll(startGameLabel,titleLabel,descriptionLabel);
                    root.getChildren().addAll(character,lifeLabel,keepGoingLabel);
                    keepGoingAnimation.start();
                    for(int i =0;i<enemys.length;i++)
                    {
                         root.getChildren().add(enemys[i]);
                         enemys[i].move.start();
                    }

                    for(int j = 0;j<life.length;j++)
                        root.getChildren().add(life[j]);
                    characterHit.start();
                    backClip.stop();
                    inGameMusicClip.setCycleCount(Animation.INDEFINITE);
                    inGameMusicClip.play(1);
                    


                    }
                    break;
                case RIGHT:
                     if(!runing)
                     {    
                         runing = true;
                         character.standAnimation.stop();
                         character.runing();

                     }
                     if(character.getBoundsInParent().getMaxX() > 600)
                     {

                         backgroundMoving.start();
                         moving = true; 
                     }
                     else
                     {
                         backgroundMoving.stop();
                         moving = false;
                         character.setTranslateX(character.getTranslateX()+7);    
                     }

                     break;
                case LEFT:
                    if(!runing)
                    {
                        runing = true;
                        character.standAnimation.stop();
                        character.runing();
                        character.setScaleX(-1);
                    }
                    character.setTranslateX(character.getTranslateX()-5);
                    break;
                case UP:
                    if(!jump && road != 2)
                    {
                        jump = true;
                        character.standAnimation.stop();

                        character.jump();

                    }
                    if(runing)
                    {
                        character.runingAnimation.stop();
                        runing = false;
                    }
                    if(road == 1)
                    {
                         character.setTranslateY(character.getTranslateY()-40);
                         road = 2;
                    }
                    if(road == 0)
                    {
                         character.setTranslateY(character.getTranslateY()-40);
                         road = 1;    
                    }
                    if(shoot)
                    {
                        character.attackAnimation.stop();
                        shoot = false;
                    }
                    if(moving)
                    {
                        backgroundMoving.stop();
                    }
                    break;
                case DOWN:
                    if(road == 2)
                    {
                       character.setTranslateY(character.getTranslateY()+40);
                       road = 1; 
                    }
                    else if(road == 1 )
                    {
                       character.setTranslateY(character.getTranslateY()+40);
                       road = 0; 
                    }

                    break;
                case W:
                    if(!shoot)
                    {
                        shoot = true;
                        character.standAnimation.stop();

                        if(runing)
                        {
                            character.runingAnimation.stop();
                            runing = false;
                        }
                        if(jump)
                        {
                            character.jumpAnimation.stop();
                            jump = false;
                        }
                        character.attack();
                        if(moving)
                        {
                            backgroundMoving.stop();
                        }
                    }
                    break;
                case E:
                    if(character.getBoundsInParent().getMaxX() <= 4)
                    {
                        if(!jumpOver)
                        {
                            jumpOver = true;
                            character.standAnimation.stop();
                            character.jump();
                        }

                        if(!toAir)
                        {
                            toAir = true;
                            character.setTranslateY(character.getTranslateY() - 70);
                        }
                        else if(!inAir)
                        {
                            inAir = true;
                            character.setTranslateX(character.getTranslateX() + 80);
                        }
                        else if(!onGround)
                        {
                            onGround = true;
                             character.setTranslateY(character.getTranslateY() + 70);

                             for(int i = 0;i<enemys.length;i++)
                             {

                                if(colideCharacterJumpEnemy(enemys[i], character))
                                {
                                    enemys[i].setTranslateX(1100);
                                    enemys[i].setTranslateY(420 + roadNumber[r.nextInt(3)]);
                                    enemys[i].Health = 2;

                                }
                             }
                        }
                    }

            }
        }
        else
         {
             if(gameOver)
             {
                 if(k.getCode() == KeyCode.SPACE)
                 {
                     startGame();
                 }
             }
         }
        });
        scene.setOnKeyReleased((KeyEvent k) -> {
            switch(k.getCode())
           {
                case RIGHT:
                    if(runing)
                    {
                        backgroundMoving.stop();
                     runing = false;
                     character.runingAnimation.stop();
                     character.stand();
                    }
                    break;
                case LEFT:
                    if(runing)
                    {
                     runing = false;
                     character.runingAnimation.stop();
                     character.stand();
                     character.setScaleX(1);
                    }
                    break;
                case UP:
                    if(jump)
                    {
                        jump = false;
                        character.jumpAnimation.stop();
                        character.stand();
                    }
                    break;
                
                case W:
                    if(shoot)
                    {
                        
                        shoot = false;
                        character.attackAnimation.stop();
                        character.stand();
                        missleAction();
                        shootClip.play(0.3);
                    }
                    break;
                case E:
                    if(jumpOver)
                    {
                        jumpOver = false;
                        character.jumpAnimation.stop();
                        character.stand();
                        if(inAir && toAir && onGround)
                        {
                            inAir = false;
                            toAir = false;
                            onGround = false;
                        }
                    }
            }    
            
        });
        
        primaryStage.setTitle("Warrior Girl!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
    * Function that create missiles, set moving animation and all mechanisms
    * If missile collide with enemy missile is removed from app
    */
    private void missleAction(){
        
        Missile m = new Missile();
                        root.getChildren().add(m);
                        m.setTranslateY(character.getTranslateY()+10);
                        m.setTranslateX(character.getTranslateX()+70);
                        AnimationTimer missleMoving = new AnimationTimer(){
                            @Override
                            public void handle(long now) {
                                m.setTranslateX(m.getTranslateX()+ 5);
                                if(colideMissileWall(m))
                                {
                                    root.getChildren().remove(m);
                                    m.setTranslateY(-10000);
                                }
                                for(int i = 0;i<enemys.length;i++)
                                {
                                if(colideMissleEnemy(m,enemys[i]) && onGround == false)
                                {
                                    
                                    root.getChildren().remove(m);
                                    m.setTranslateY(-10000);
                                    if(enemys[i].Health == 1)
                                    {
                                        
                                        enemys[i].setTranslateX(1500 + r.nextInt(500 - 300)+300);
                                        enemys[i].setTranslateY(420 + roadNumber[r.nextInt(3)]);
                                        enemys[i].Health = 2;
                                        
                                    }
                                    else
                                    {
                                        shoutClip.play(0.3);
                                        enemys[i].move.stop();
                                        enemys[i].Damage();
                                        enemys[i].Health--;
                                        enemys[i].move.start();
                                    }
                                    
                                    
                                }
                                }
                            }
                            
                        };
                        missleMoving.start();
    }
    /**
     * Check out if missile colide with enemy
     * @param missle Node of object Missile
     * @param enemy Node of object Enemy
     * @return 
     */
    public Boolean colideMissleEnemy(Node missle,Node enemy)
    {
        return ((enemy.getBoundsInParent().getMaxY() - missle.getBoundsInParent().getMaxY() == 0) && ( enemy.getBoundsInParent().intersects(missle.getBoundsInParent())));
    }
    /**
     * Check out if missile colide with wall
     * @param missile Node of object Missile
     * @return 
     */
    public Boolean colideMissileWall(Node missile)
    {
        return missile.getBoundsInParent().getMaxX() >1200;
    }
    /**
     * check out if character colide with enemy
     * @param character Node of object Character
     * @param enemy Node of object Enemy
     * @return 
     */
    public Boolean colideCharacterEnemy(Node character, Node enemy)
    {
        return ((enemy.getBoundsInParent().getMaxY() - character.getBoundsInParent().getMaxY() == 0) && ( enemy.getBoundsInParent().intersects(character.getBoundsInParent())));
    }
    /**
     * check out if enemy colide with wall
     * @param enemy
     * @return 
     */
    public Boolean colideEnemyWall(Node enemy)
    {
        return enemy.getBoundsInParent().getMinX() < 0;
    }
    /**
     * Stops all animation and music in game, remove all enemys and labels.
     * Add new music and labels
     */
    
    public Boolean colideCharacterJumpEnemy(Node enemy, Node character)
    {
       return (character.getBoundsInParent().getMaxY() - enemy.getBoundsInParent().getMaxY() == 0) && ( enemy.getBoundsInParent().intersects(character.getBoundsInParent()));
    }
    public void stopGame()
    {
        for(int i= 0;i<enemys.length;i++)
        {
            root.getChildren().remove(enemys[i]);
            enemys[i].move.stop();
            enemys[i].enemyAnimation.stop();
            enemys[i].setTranslateY(-10000);
        }
        for(int j = 0;j<life.length;j++)
            root.getChildren().remove(life[j]);
        root.getChildren().removeAll(lifeLabel,keepGoingLabel);
        keepGoingAnimation.start();
        character.standAnimation.stop();
        backgroundMoving.stop();
        inGameMusicClip.stop();
        root.getChildren().add(restartLabel);
        restartLabelAnimation.start();
        gameOver = true;
        if(runing)
        {
            character.runingAnimation.stop();
        }
        if(jump)
        {
            character.jumpAnimation.stop();
        }
        if(shoot)
        {
            character.attackAnimation.stop();
        }
    }
    /**
     * editing all objects in game to default varables
     */
    public void startGame()
    {
        character.setTranslateX(0);
        character.setTranslateY(420);
        root.getChildren().removeAll(winLabel,restartLabel,gameOverLabel);
        root.getChildren().addAll(lifeLabel,keepGoingLabel);
        restartLabelAnimation.stop();
        keepGoingAnimation.start();
        backgroundView.setLayoutX(0);
        moveVariable = 0;
        inGameMusicClip.play(1);
        gameOver = false;
        health = 5;
        road = 1;
        for(int i = 0;i<life.length;i++)
        {
            root.getChildren().add(life[i]);
        }
        for(int j = 0;j<enemys.length;j++)
        {
            root.getChildren().add(enemys[j]);
            enemys[j].setTranslateY(420 + roadNumber[r.nextInt(3)]);
            enemys[j].setTranslateX(800 + (r.nextInt(1000 - 400) + 400));
            enemys[j].move.start();
            enemys[j].run();
        }
        
        
    }
    
}
