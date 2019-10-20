import java.util.ArrayList;
import java.util.Comparator;

public class Movies {
	private String image,name,date,overview,backdrop,id;
    private int rate;
    public ArrayList<String> trailers = new ArrayList<>();



    public Movies(String image, String name, String date, String overview, String backdrop, int rate, String id) {
        this.image = image;
        this.name = name;
        this.date = date;
        this.overview = overview;
        this.backdrop = backdrop;
        this.rate = rate;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public int getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }

    public void putTrailer(String s){
        trailers.add(s);
    }

    public String getTrailer(int i){
        return trailers.get(i);
    }

    public int trailerNum(){
        return trailers.size();
    }


    public void clear(){
        if(trailerNum()>0)
            trailers.clear();

    }



    public static Comparator<Movies> rateComparator = new Comparator<Movies>() {
        @Override
        public int compare(Movies m1, Movies m2) {
            int mRate1 = m1.getRate();
            int mRate2 = m2.getRate();
            return mRate2-mRate1;
        }
    };

    public static Comparator<Movies> nameComparator = new Comparator<Movies>() {
        @Override
        public int compare(Movies m1, Movies m2) {
            String mName1 = m1.getName().toUpperCase();
            String mName2 = m2.getName().toUpperCase();
            return mName1.compareTo(mName2);
        }
    };

    public ArrayList<String> getTrailers() {
        return trailers;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
