package edu.illinois.cs.alignment.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winglam on 10/30/15.
 */
public class Activity implements Comparable<Activity> {
    private String name;
    private List<InputField> fields;

    public Activity(String name, List<InputField> fields) {
        if (fields == null) {
            this.fields = new ArrayList<>();
        } else {
            this.fields = fields;
        }
        this.name = name;
    }

    public List<InputField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<InputField> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode()) + name.hashCode();
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Activity other = (Activity) obj;
        if (fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else if (!fields.equals(other.fields) || !name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override public int compareTo(Activity arg) {
        if (this.equals(arg)) {
            return 0;
        }
        if (fields.size() > arg.getFields().size()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override public String toString() {
        return "Activity{" + "name=" + name +
               ", fields=" + fields +
               '}';
    }
}
