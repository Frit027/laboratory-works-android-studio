package com.mirea.lab3_1;

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
    private static final int SCHEMA = 1;
    static final String COLUMN_ID = "_id";
    static final String COLUMN_FULL_NAME = "name";
    static final String COLUMN_TIME_TO_ADD = "time";

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
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_TIME_TO_ADD + " DATETIME DEFAULT CURRENT_TIME" +
                ");");

        insertData(db);
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
            String name = studentsList.get(i);

            values.put(COLUMN_FULL_NAME, name);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public String getName(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_FULL_NAME + " FROM " + TABLE_NAME + ";", null);
        ArrayList<String> names = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(0);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
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
        studentsList.add("Логецкая Светлана Александровна"); studentsList.add("Магомедов Мурад Магамедович");
        studentsList.add("Миночкин Антон Андреевич");        studentsList.add("Опарин Иван Алексеевич");
        studentsList.add("Паршаков Никита Алексеевич");      studentsList.add("Самохин Олег Романович");
        studentsList.add("Сахаров Владислав Игоревич");      studentsList.add("Смирнов Сергей Юрьевич");
        studentsList.add("Трошин Дмитрий Вадимович");        studentsList.add("Чехуров Денис Александрович");
        studentsList.add("Эльшейх Самья Ахмед");             studentsList.add("Юров Илья Игоревич");
    }
}
