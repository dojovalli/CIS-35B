
// PlottingPanel.java
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
 
class PlottingPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6522235573073868456L;

// Data sampling
  double[] data = {39,31,15,-3,-17,-21,-9,25,87};
 
  // Define size of plotting panel
  static int W=600;
  static int H=400;
 
  public PlottingPanel()
  {
    setPreferredSize(new java.awt.Dimension(W,H));
  }
 
  @Override public void paint(Graphics gr)
  {
    // horizontal scaling
    double xScale = W / data.length;   
 
    // vertical scaling
    double yMinimum = -21;  // should use a for loop to find it out
    double yMaximum = 87;   // should use a for loop to find it out
    double yScale = H / (yMaximum-yMinimum);
 
    // actually plotting the graph
    int xPrev=-1, yPrev=-1;
    for (int i=0;i<data.length;i++)
    {
      int x = (int) ( i*xScale );
      int y = (int) ( (data[i] - yMinimum) *yScale );
      y = H-y;  // up-side down
      plot(gr,x,y);
      // join data point with straight line
      if (xPrev!=-1) gr.drawLine(xPrev,yPrev,x,y);
      xPrev=x; yPrev=y;
    }
  }
 
  public void plot(Graphics gr, int x, int y)
  {
    // draw a small rectangle to represent the data point
    gr.setColor(java.awt.Color.RED);
    int width = 10;
    int height = 10;
    int xUpperLeft = x-width/2;
    int yUpperLeft = y-height/2;
    gr.fillRect(xUpperLeft, yUpperLeft, width, height);
  }
 
  public static void main(String[] args) throws Exception
  {
    JFrame frame=new JFrame("Plotter");
    frame.add(new PlottingPanel());
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}