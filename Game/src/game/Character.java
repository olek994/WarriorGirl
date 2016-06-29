
package game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Character extends Pane {
 
    private final Image im = new Image(getClass().getResourceAsStream("character.png"));
    private ImageView imageView = new ImageView(im);
    
   
    
    
    
    public SpriteAnimation standAnimation;
    public SpriteAnimation runingAnimation;
    public SpriteAnimation jumpAnimation;
    public SpriteAnimation attackAnimation;
    public SpriteAnimation DamageAnimation;
    
    public Character()
    {
        
        stand();
        getChildren().addAll(imageView);
    }
    /**
     *  Create stand animation
     */
    public void stand()
    {
        int column = 6;
        int count = 6;
        int offset_x= 0;
        int offset_y= 0;
        int width= 41;
        int height= 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+40);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        standAnimation = new SpriteAnimation(this.imageView,Duration.millis(1000),count,column,offset_x,offset_y,width,height);
        standAnimation.setCycleCount(Animation.INDEFINITE);
        standAnimation.play();
        
    }        
                
    /**
     *  Create runing animation
     */
    public void runing()
    {
        
        int column = 6;
        int count = 6;
        int offset_x = 47;
        int offset_y = 61;
        int width = 43;
        int height = 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+40);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        runingAnimation = new SpriteAnimation(imageView,Duration.millis(1000),count,column,offset_x,offset_y,width,height);
        runingAnimation.setCycleCount(Animation.INDEFINITE);
        runingAnimation.play();
    }
    /**
     *  Create jump animation
     */
    public void jump()
    {
        int column = 1;
        int count = 1;
        int offset_x = 40;
        int offset_y = 998;
        int width = 37;
        int height = 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+40);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        jumpAnimation = new SpriteAnimation(imageView,Duration.millis(2000),count,column,offset_x,offset_y,width,height);
        jumpAnimation.setCycleCount(Animation.INDEFINITE);
        jumpAnimation.play();
    }
    /**
     *  Create attack animation
     */
    public void attack()
    {
        int column = 4;
        int count = 4;
        int offset_x = 45;
        int offset_y = 1125;
        int width = 42;
        int height = 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+40);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        attackAnimation = new SpriteAnimation(imageView,Duration.millis(2000),count,column,offset_x,offset_y,width,height);
        attackAnimation.setCycleCount(Animation.INDEFINITE);
        attackAnimation.play();
    }

   
}
