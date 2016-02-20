package com.lamyatweng.mmugraduationstaff;

import com.firebase.client.Firebase;

public interface Constants {
    String FIREBASE_STRING_STAFF_REF = "https://mmugraduationstaff.firebaseio.com/";
    String FIREBASE_STRING_ROOT_REF = "https://mmugraduation.firebaseio.com/";
    String FIREBASE_STRING_STUDENTS_REF = "https://mmugraduation.firebaseio.com/students";
    String FIREBASE_STRING_PROGRAMMES_REF = "https://mmugraduation.firebaseio.com/programmes";
    String FIREBASE_STRING_CONVOCATIONS_REF = "https://mmugraduation.firebaseio.com/convocations";
    String FIREBASE_STRING_SESSIONS_REF = "https://mmugraduation.firebaseio.com/sessions";
    String FIREBASE_STRING_SEATS_REF = "https://mmugraduation.firebaseio.com/seats";

    Firebase FIREBASE_REF_CONNECTED_STUDENT = new Firebase("https://mmugraduation.firebaseio.com/.info/connected");
    Firebase FIREBASE_REF_CONNECTED_STAFF = new Firebase("https://mmugraduationstaff.firebaseio.com/.info/connected");
    Firebase FIREBASE_REF_ROOT = new Firebase("https://mmugraduation.firebaseio.com/");
    Firebase FIREBASE_REF_STAFF = new Firebase("https://mmugraduationstaff.firebaseio.com/");
    Firebase FIREBASE_REF_STUDENTS = new Firebase("https://mmugraduation.firebaseio.com/students");
    Firebase FIREBASE_REF_PROGRAMMES = new Firebase("https://mmugraduation.firebaseio.com/programmes");
    Firebase FIREBASE_REF_CONVOCATIONS = new Firebase("https://mmugraduation.firebaseio.com/convocations");
    Firebase FIREBASE_REF_SESSIONS = new Firebase("https://mmugraduation.firebaseio.com/sessions");
    Firebase FIREBASE_REF_SEATS = new Firebase("https://mmugraduation.firebaseio.com/seats");
    Firebase FIREBASE_REF_NEWS = new Firebase("https://mmugraduation.firebaseio.com/news");
    Firebase FIREBASE_REF_CONNECTED = new Firebase("https://mmugraduation.firebaseio.com/.info/connected");

    String FIREBASE_ATTR_SEATS_SESSIONID = "sessionID";
    String FIREBASE_ATTR_SEATS_ID = "id";
    String FIREBASE_ATTR_SESSIONS_ID = "id";
    String FIREBASE_ATTR_SESSIONS_ROWSIZE = "rowSize";
    String FIREBASE_ATTR_SESSIONS_CONVOCATIONYEAR = "convocationYear";
    String FIREBASE_ATTR_STUDENTS_STATUS = "status";
    String FIREBASE_ATTR_STUDENTS_ID = "id";
    String FIREBASE_ATTR_SESSIONS_COLUMNSIZE = "columnSize";

    String TITLE_NEWS = "News";
    String TITLE_PROGRAMME = "Programme";
    String TITLE_ACCOUNT = "Account";
    String TITLE_STUDENT = "Student";
    String TITLE_GRADUATION = "Graduation";
    String TITLE_CONVOCATION = "Convocation";
    String TITLE_SEAT = "Seat";
    String TITLE_SESSION = "Session";
    String TITLE_LOGOUT = "Logout";

    String MENU_ADD = "Add";
    String MENU_SAVE = "Save";
    String MENU_EDIT = "Edit";
    String MENU_DELETE = "Delete";

    String STUDENT_STATUS_ACTIVE = "Active";
    String STUDENT_STATUS_COMPLETED = "Completed";
    String STUDENT_STATUS_PENDING_APPROVAL = "Pending approval";

    String SEAT_STATUS_AVAILABLE = "Available";
    String SEAT_STATUS_OCCUPIED = "Occupied";
    String SEAT_STATUS_DISABLED = "Disabled";

    String PROGRAMME_LEVEL_DIPLOMA = "Diploma";
    String PROGRAMME_LEVEL_BACHELOR = "Bachelor's Degree";
    String PROGRAMME_LEVEL_MASTER = "Master's Degree";
    String PROGRAMME_LEVEL_DOCTORATE = "Doctorate";

    String EXTRA_CONVOCATION_KEY = "com.lamyatweng.mmugraduationstaff.CONVOCATION_KEY";
    String EXTRA_CONVOCATION_YEAR = "com.lamyatweng.mmugraduationstaff.CONVOCATION_YEAR";
    String EXTRA_SESSION_KEY = "com.lamyatweng.mmugraduationstaff.SESSION_KEY";
    String EXTRA_SESSION_ID = "com.lamyatweng.mmugraduationstaff.SESSION_ID";
    String EXTRA_SESSION_COLUMN_SIZE = "com.lamyatweng.mmugraduationstaff.SESSION_COLUMN_SIZE";
    String EXTRA_SESSION_ROW_SIZE = "com.lamyatweng.mmugraduationstaff.SESSION_ROW_SIZE";
    String EXTRA_SESSION_CONVOCATION_YEAR = "com.lamyatweng.mmugraduationstaff.SESSION_CONVOCATION_YEAR";
    String EXTRA_SEAT_ID = "com.lamyatweng.mmugraduationstaff.SEAT_ID";
    String EXTRA_SEAT_KEY = "com.lamyatweng.mmugraduationstaff.SEAT_KEY";
}
