import java.io.StringWriter;
import java.io.Writer;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.Jsonable;


public class Blog implements Jsonable {
    public int id;
    public String title;
    public String body;

    enum keys implements JsonKey {
        ID("id"),
        TITLE("title"),
        BODY("body");

        private final Object value;

        /**
         * Instantiates a JsonKey with the provided value.
         *
         * @param value represents a valid default for the key.
         */
        keys(final Object value) {
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            /* Can represent a valid default, error value, or null adhoc for the JsonKey. See the javadocs for more
             * information about its intended use. */
            return this.value;
        }
    }

    public Blog() {
    }

    public Blog(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Blog(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
  /*
  private Movie() {
    this.title = (String)se.jensen.caw21.bjorn.Movie.keys.TITLE.getValue();
    this.rating = (int)se.jensen.caw21.bjorn.Movie.keys.RATING.getValue();
  }
  */

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final Exception e) {
            /* See java.io.StringWriter. */
        }
        return writable.toString();
    }

    @Override
    public void toJson(final Writer writable) {
        try {
            final JsonObject json = new JsonObject();
            json.put(keys.TITLE.getKey(), this.getTitle());
            json.put(keys.BODY.getKey(), this.getBody());
            json.put(keys.ID.getKey(), this.getID());
            json.toJson(writable);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public String toString() {
        return "JsonSimpleExample [id=\" + this.id + \", title=" + this.title + ", rating=" + this.body + "]";
    }
}