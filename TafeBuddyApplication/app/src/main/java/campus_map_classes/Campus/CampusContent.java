package campus_map_classes.Campus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CampusContent {

    /**
     * An array of sample Campuses.
     */
    public static final List<Campus> CAMPUSES = new ArrayList<>();

    /**
     * A map of Campuses, by ID.
     */
    public static final Map<String, Campus> CAMPUS_MAP = new HashMap<String, Campus>();


    static {
        // Add some sample Campuses.
        addCampus(new Campus("1","Adelaide City Campus", "College",-34.923985, 138.594936));
        addCampus(new Campus("2","Tonsley Campus","Technical School",-35.008921, 138.571065));
        addCampus(new Campus("3","Urrbrae Campus","Adult Education School",-34.967092, 138.625363));
        //addCampus(new Campus("4","Morphetville Campus","Education",0.0 ,0.0));
        addCampus(new Campus("5","Regency Campus","College",-34.873084, 138.568552));
        addCampus(new Campus("6","Port Adelaide Campus","Technical School",-34.843752, 138.500053));
        addCampus(new Campus("7","Tea Tree Gully Campus","Training Centre",-34.832307, 138.695694));
        addCampus(new Campus("8","Giles Plains Campus","Higher Education",-34.851539, 138.651755));
        addCampus(new Campus("9","Parafield Campus","Learning Centre",-34.788651, 138.633127));
        addCampus(new Campus("10","Elizabeth Campus","College",-34.712860, 138.671317));
        addCampus(new Campus("11","Gawler Campus","College",-34.597192, 138.750082));
        addCampus(new Campus("12","Noarlunga Campus","Higher Learning School",-35.140305, 138.496986));
        addCampus(new Campus("12","Mount Barker Campus","Higher Learning School",-35.068815, 138.854308));

    }

    private static void addCampus(Campus campus) {
        CAMPUSES.add(campus);
        CAMPUS_MAP.put(campus.id,campus);
    }

    /**
     * Setting up the Campus Class
     */
    public static class Campus {
        public final String id;
        public final String campusName;
        public final String campusDetails;
        public final Double campusLat;
        public final Double campusLng;

        public Campus(String id, String CampusName, String CampusDetails, Double CampusLat, Double CampusLng) {
            this.id = id;
            this.campusName = CampusName;
            this.campusDetails = CampusDetails;
            this.campusLat = CampusLat;
            this.campusLng = CampusLng;
        }

        @Override
        public String toString() {
            return campusName + " \n" + campusDetails;
        }
    }
}
