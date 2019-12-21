package Visualizers;

import Game.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldVisualizer {
  JFrame frame;
  JTextArea textArea;
  JButton pauseContinueButton;
  JButton quitButton;
  Timer timer;
  World world;



  public WorldVisualizer(World world){
    this.world = world;
    this.initialize();
  }

  private void initialize(){
    this.frame = new JFrame("Animals simulation");
    frame.setSize(800,800);
    frame.setLayout(null);
    frame.setVisible(true);

    this.textArea=new JTextArea("");
    textArea.setBounds(100,100, 550,650);
    textArea.setEditable(false);

    this.pauseContinueButton = new JButton("");
    pauseContinueButton.setBounds(650,200,100,20);
    frame.add(pauseContinueButton);

    pauseContinueButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (timer == null)
          startVisualization();
        else
          pauseVisualization();
      }
    });

    this.quitButton = new JButton("Quit");
    quitButton.setBounds(650,230,100,20);
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
      }
    });

    frame.add(pauseContinueButton);
    frame.add(quitButton);

  }

  ActionListener timerListener = new ActionListener() {
    // Define an action listener to respond to events
    // from the timer.
    public void actionPerformed(ActionEvent event) {
      if(world.animalsExtinct()){
        textArea.setText(world.toString());
        pauseContinueButton.setVisible(false);
        timer.stop();
      }
      textArea.setText(world.toString());
      frame.add(textArea);
      SwingUtilities.updateComponentTreeUI(frame);
      world.passTheDay();
    }
  };

  public void startVisualization(){
    if (timer == null) {
      timer = new Timer(75, timerListener);
      timer.start();
      pauseContinueButton.setText("Pause");
    }
  }

  private void pauseVisualization() {
    if (timer != null) {
      timer.stop();
      timer = null;
      pauseContinueButton.setText("Continue");
    }
  }
}
