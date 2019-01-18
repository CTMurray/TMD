

//add toward top if missing
fileMenu.add(openAction);
openAction.addActionListener(this);
		


//add this after the save section in action performed

if(buttonPressed == openAction) {
			JFrame f2  = new JFrame("Poster View");
			JPanel p = new JPanel();
			JLabel label2;
			URL url;// = new URL("http://vignette3.wikia.nocookie.net/supermanfanfic/images/9/94/Underworld_kate_beckinsale_3_.jpg/revision/latest?cb=20090401232412");
			BufferedImage image;
			
			ImageIcon pic = new ImageIcon(trivia.getPoster());
			p.add(new JLabel(pic));
			String path = trivia.getPoster();
			
			try {
				url = new URL("http://vignette3.wikia.nocookie.net/supermanfanfic/images/9/94/Underworld_kate_beckinsale_3_.jpg/revision/latest?cb=20090401232412");
				pic = new ImageIcon(path);
				image = ImageIO.read(url);
				label2 = new JLabel(new ImageIcon(image));
				
				f2.getContentPane().add(label2);
				f2.add(label2);
				//f2.getContentPane().add(label2);
				//label2 = new JLabel(new ImageIcon(image));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//BufferedImage image = ImageIO.read(url);					
			//label2 = new JLabel(new ImageIcon(image));
		   // f2 = new JFrame();
		    //p = new JPanel();
		    //p.add(label2);
			//label2 = new JLabel(new ImageIcon(image));
			//f2.getContentPane().add(label2);
		    f2.setSize(500,400);
		    p.add(new JLabel(pic));
		    
		    f2.add(p);
		    f2.setVisible(true);
		}