
package game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Missile extends Pane {
    private final Image im = new Image(getClass().getResourceAsStream("missle.png"));
    private ImageView imageView = new ImageView(im);
    
    public SpriteAnimation missleAnimation;
    
    public Missile()
    {
        flyingMissile();
        getChildren().add(imageView);
    }
    /**
     * Create flying missile animation
     */
    public void flyingMissile()
    {
        int column = 1;
        int count = 1;
        int offset_x= 10;
        int offset_y= 10;
        int width= 20;
        int height= 30;
        
        imageView.setFitHeight(height+50);
        imageView.setFitWidth(width+40);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        missleAnimation = new SpriteAnimation(this.imageView,Duration.millis(1000),count,column,offset_x,offset_y,width,height);
        missleAnimation.setCycleCount(Animation.INDEFINITE);
        missleAnimation.play();
    }
    
    
}
