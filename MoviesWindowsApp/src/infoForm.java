import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class infoForm extends JFrame {

	private JPanel contentPane;
	Movies movie;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					infoForm frame = new infoForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public infoForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 682, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblMovieimage = new JLabel("movieImage");
		lblMovieimage.setBounds(10, 11, 162, 260);
		contentPane.add(lblMovieimage);
		System.out.println("First");
	}
	public infoForm(Movies m) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 620, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblMovieimage = new JLabel("movieImage");
		lblMovieimage.setBounds(10, 11, 109, 191);
		contentPane.add(lblMovieimage);
		movie = m;
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new URL(m.getImage()));
			Image dimg = img.getScaledInstance(lblMovieimage.getWidth(), lblMovieimage.getHeight(),
			        Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
			lblMovieimage.setIcon(imageIcon);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Second");
		System.out.println(m.getName());
		
	}
}
