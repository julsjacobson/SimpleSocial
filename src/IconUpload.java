import java.awt.*;  
import java.awt.event.*;  
import java.awt.image.*;  
import javax.swing.*;  
import java.awt.Frame;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  

class IconUpload extends Canvas  {  
	Image img;  
	public IconUpload(Image img) {  
		this.img = img;  
	}  
	
	public void paint(Graphics g) {  
		if (img != null)  {  
			g.drawImage(img, 0, 0, 200, 200, this);  
		}  
	}  	
	public void setImage(Image img)  {  
		this.img = img;  
	}  
}  