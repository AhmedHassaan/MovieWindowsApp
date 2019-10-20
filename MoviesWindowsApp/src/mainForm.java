import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class mainForm {

	private JFrame frame;
	ArrayList<Movies> moviesList;
	private final Map<String, ImageIcon> imageMap;
	private DefaultListModel dlm;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainForm window = new mainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainForm() {
		imageMap = new HashMap<String, ImageIcon>();
		moviesList = new ArrayList<>();
		dlm = new DefaultListModel();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JList list = new JList(dlm);
		list.setBounds(10, 11, 682, 397);
		frame.getContentPane().add(list);
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillData();
				list.setCellRenderer(new AsyncTaskTestRenderer());
			}
		});
		btnLoad.setBounds(10, 420, 123, 47);
		frame.getContentPane().add(btnLoad);
		
		JButton btnInfo = new JButton("Info");
		btnInfo.setBounds(143, 420, 123, 47);
		frame.getContentPane().add(btnInfo);
		
	}
	private void fillData() {
		
		if(fetchData()) {
			System.out.println("Success");
			dlm.removeAllElements();
			for(int i=0;i<moviesList.size();i++) {
				System.out.println(moviesList.get(i).getName());
				dlm.addElement(moviesList.get(i).getName());
				try {
					imageMap.put(moviesList.get(i).getName(), new ImageIcon(new URL(moviesList.get(i).getImage())));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else
			System.out.println("Failed");
	}
	

	public boolean fetchData() {
		String appKey = "9f8f4a4070f14920238abc2d569f283b";
        String en = "en-US";
        String movieJson = null;
		String builtMovie;
        String baseURL = "http://api.themoviedb.org/3/movie/" + "upcoming" + "?";
        final String api_key = "api_key";
        final String lang = "language";
        builtMovie = baseURL + lang + "=" + en + "&" + api_key + "=" + appKey;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(builtMovie);
//            Log.v(LOG_TAG, "built uri " + builtMovie.toString());
            System.out.println("built uri " + builtMovie);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return false;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return false;
            }
            movieJson = buffer.toString();
//            Log.v(LOG_TAG, "Movie JSON String: " + movieJson);
            System.out.println(movieJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }
        }

        try {
            return getData(movieJson);
        } catch (JSONException e) {
            System.out.println(e.getMessage() + "  " + e);
//            e.printStackTrace();
        }
        
        return false;
	}
	
	private boolean getData(String moviesStr) throws JSONException{
        if(moviesStr == null)
            return false;
        final String title = "title";
        final String image = "poster_path";
        final String result = "results";
        final String backDrop = "backdrop_path";
        final String desc = "overview";
        final String date = "release_date";
        final String rate = "vote_average";
        final String id = "id";
        String baseImageNormal = "https://image.tmdb.org/t/p/w185";
        String baseImageBig = "https://image.tmdb.org/t/p/w300";

        JSONObject movieJson = new JSONObject(moviesStr);
        JSONArray moviesArray = movieJson.getJSONArray(result);

        for(int i=0;i<moviesArray.length();i++){
            JSONObject oneMovie = moviesArray.getJSONObject(i);
            String oneTitle = oneMovie.getString(title);
            String oneImage = baseImageNormal + oneMovie.getString(image);
            String oneDesc = oneMovie.getString(desc);
            String oneDate = oneMovie.getString(date);
            String back = baseImageBig + oneMovie.getString(backDrop);
            int oneRate = oneMovie.getInt(rate);
            int oneId = oneMovie.getInt(id);
            Movies m = new Movies(oneImage,oneTitle,oneDate,oneDesc,back,oneRate, Integer.toString(oneId));
            moviesList.add(m);
//            Log.v(LOG_TAG, "Movie Data : Name : " + moviesList.get(i).getName() + "    Image : " + moviesList.get(i).getImage());
        }
        return true;
    }
	
	public class AsyncTaskTestRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
}
