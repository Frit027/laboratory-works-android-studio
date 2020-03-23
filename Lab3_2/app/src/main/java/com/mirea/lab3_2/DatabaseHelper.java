package com.mirea.lab3_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.db";
    static final String TABLE_NAME = "student";
    private static final int SCHEMA = 2;
    static final String COLUMN_ID = "_id";
    static final String COLUMN_FULL_NAME = "name";
    static final String COLUMN_TIME_TO_ADD = "time";
    static final String COLUMN_FIRST_NAME = "first_name";
    static final String COLUMN_LAST_NAME = "second_name";
    static final String COLUMN_PATRONYMIC = "patronymic";

    private ArrayList<String> studentsList;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        fillArrayList();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_PATRONYMIC + " TEXT, " +
                COLUMN_TIME_TO_ADD + " DATETIME DEFAULT CURRENT_TIME" +
                ");");
    }

    public void insertData(SQLiteDatabase db) {
        Random random = new Random();
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int pos;

        while (randomNumbers.size() != 5) {
            pos = random.nextInt(studentsList.size());
            if (randomNumbers.indexOf(pos) == -1) {
                randomNumbers.add(pos);
            }
        }

        ContentValues values = new ContentValues();
        for (int i : randomNumbers) {
            String[] words = studentsList.get(i).split("\\s");

            values.put(COLUMN_FIRST_NAME, words[1]);
            values.put(COLUMN_LAST_NAME, words[0]);
            values.put(COLUMN_PATRONYMIC, words[2]);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public String getName(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_LAST_NAME + ", " +
                COLUMN_FIRST_NAME + ", " + COLUMN_PATRONYMIC + " FROM " + TABLE_NAME + ";", null);
        ArrayList<String> names = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String lastName = cursor.getString(0);
                String patronymic = cursor.getString(2);
                String firstName = cursor.getString(1);
                String name = lastName + " " + firstName + " " + patronymic;
                names.add(name);
            }
            while(cursor.moveToNext());
        }
        cursor.close();

        for (String name: studentsList) {
            if (names.indexOf(name) == -1) {
                return name;
            }
        }

        return studentsList.get(0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {

            db.execSQL("CREATE TABLE IF NOT EXISTS new_table" +
                    " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIME_TO_ADD + " DATETIME DEFAULT CURRENT_TIME" +
                    ");");

            db.execSQL("insert into new_table (_id, time) select _id, time from student;");
            db.execSQL("ALTER TABLE new_table ADD COLUMN " + COLUMN_LAST_NAME + " TEXT");
            db.execSQL("ALTER TABLE new_table ADD COLUMN " + COLUMN_FIRST_NAME + " TEXT");
            db.execSQL("ALTER TABLE new_table ADD COLUMN " + COLUMN_PATRONYMIC + " TEXT");

            Cursor cursor = db.rawQuery("SELECT " + COLUMN_FULL_NAME + ", " + COLUMN_ID
                                        + " FROM " + TABLE_NAME + ";", null);

            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> idList = new ArrayList<>();

            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(0);
                    String id = cursor.getString(1);
                    names.add(name);
                    idList.add(id);
                }
                while(cursor.moveToNext());
            }
            cursor.close();

            ContentValues values = new ContentValues();
            for (int i = 0; i < names.size(); i++) {
                String[] words = names.get(i).split("\\s");

                values.put(COLUMN_FIRST_NAME, words[1]);
                values.put(COLUMN_LAST_NAME, words[0]);
                values.put(COLUMN_PATRONYMIC, words[2]);
                db.update("new_table", values, "_id = ?", new String[] { idList.get(i) });
            }

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("ALTER TABLE new_table RENAME TO student");
        }
    }

    private void fillArrayList() {
        studentsList = new ArrayList<>();
        studentsList.add("Алексеев Никита Евгеньевич");      studentsList.add("Баранников Вадим Сергеевич");
        studentsList.add("Булыгин Андрей Генадьевич");       studentsList.add("Геденидзе Давид Темуриевич");
        studentsList.add("Горак Никита Сергеевич");          studentsList.add("Грачев Александр Альбертович");
        studentsList.add("Гусейнов Илья Алексеевич");        studentsList.add("Жарикова Екатерина Сергеевна");
        studentsList.add("Журавлев Владимир Евгеньевич");    studentsList.add("Загребельный Александр Русланович");
        studentsList.add("Иванов Алексей Дмитриевич");       studentsList.add("Карипова Лейсан Вильевна");
        studentsList.add("Копотов Михаил Алексеевич");       studentsList.add("Копташкина Татьяна Алексеевна");
        studentsList.add("Косогоров Кирилл Станиславович");  studentsList.add("Кошкни Артём Сергеевич");
        studentsList.add("Легецкая Светлана Александровна"); studentsList.add("Магомедов Мурад Магамедович");
        studentsList.add("Миночкин Антон Андреевич");        studentsList.add("Опарин Иван Алексеевич");
        studentsList.add("Паршаков Никита Алексеевич");      studentsList.add("Самохин Олег Романович");
        studentsList.add("Сахаров Владислав Игоревич");      studentsList.add("Смирнов Сергей Юрьевич");
        studentsList.add("Трошин Дмитрий Вадимович");        studentsList.add("Чехуров Денис Александрович");
        studentsList.add("Эльшейх Самья Ахмед");             studentsList.add("Юров Илья Игоревич");
    }
}
