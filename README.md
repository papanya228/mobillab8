# mobilLab8

## Задание:
 1. Разработать мобильное приложение на основе базы данных для
реализации команд добавить, удалить, обновить данные;
## Решение:
1)Создадим проект, добавим три поля для ввода и 3 кнопки
2)Добавим обработчики кнопок:
```Java
public class MainActivity extends AppCompatActivity {

    Button buttonAdd,buttonRead,buttonClear,buttonDelete,buttonUpdate;
    EditText etId,etName,etPhone;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = (Button) findViewById(R.id.addButton);
        buttonAdd.setOnClickListener(this::Add);

        buttonRead = (Button) findViewById(R.id.readButton);
        buttonRead.setOnClickListener(this::Read);

        buttonDelete = (Button) findViewById(R.id.deleteButton);
        buttonDelete.setOnClickListener(this::Del);

        etId = (EditText) findViewById(R.id.idTextView);
        etName = (EditText) findViewById(R.id.nameTextView);
        etPhone = (EditText) findViewById(R.id.PhoneTextView);

        dbHelper = new DBHelper(this);

    }

    private void Read(View view) {
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email =  etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null,
                null, null, null); // все поля без сортировки и группировки

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
            do {
                Log.d("mLog", "ID =" + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", email = " + cursor.getString(emailIndex));

            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();

        dbHelper.close();

    }

    private void Add(View view) {
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_PHONE, email);
        database.insert(DBHelper.TABLE_USERS, null, contentValues);

        dbHelper.close();

    }

    private void Del(View view){
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (!ID.equalsIgnoreCase(""))
        {
            int delCount = database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + "= " + ID, null);
            Log.d("mLog", "Удалено строк = " + delCount);
        }

        dbHelper.close();

    }

}
```
3)Добавим класс базы данных:
```Java
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myBase";
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "PhoneNumber";

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_USERS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_PHONE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_USERS);
        onCreate(sqLiteDatabase);

    }
}
```
4)Проверим работаспособность)
