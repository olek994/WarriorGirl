
package game;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Enemy extends Pane {
    private final Image im = new Image(getClass().getResourceAsStream("Enemy.png"));
    private ImageView imageView = new ImageView(im);
    public Integer Health = 2;
    
    public SpriteAnimation enemyAnimation;
    public SpriteAnimation DamageAnimation;
    
    public Enemy()
    {
        run();
        
        getChildren().add(imageView);
    }
    /**
     * Create run animation
     */
    public void run() 
    {
        int column = 6;
        int count = 6;
        int offset_x= 5;
        int offset_y= 68;
        int width= 46;
        int height= 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+20);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        enemyAnimation = new SpriteAnimation(this.imageView,Duration.millis(1000),count,column,offset_x,offset_y,width,height);
        enemyAnimation.setCycleCount(Animation.INDEFINITE);
        enemyAnimation.play();
        
    }
    /**
     * Create damage animation
     */
    public void Damage()
    {
        int column = 1;
        int count = 1;
        int offset_x= 5;
        int offset_y= 280;
        int width= 55;
        int height= 50;
        
        imageView.setFitHeight(height+40);
        imageView.setFitWidth(width+20);
        imageView.setViewport(new Rectangle2D(offset_x,offset_y,width,height));
        DamageAnimation = new SpriteAnimation(this.imageView,Duration.millis(1000),count,column,offset_x,offset_y,width,height);
        DamageAnimation.setCycleCount(Animation.INDEFINITE);
        DamageAnimation.play();
    }
    
    public AnimationTimer move = new AnimationTimer()
    {
        @Override
        public void handle(long now) {
            setTranslateX(getTranslateX()-3);
        }
        
    };
   
}
