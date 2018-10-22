package es.iessaladillo.pedrojoya.pr03.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr03.R;
import es.iessaladillo.pedrojoya.pr03.data.local.Database;
import es.iessaladillo.pedrojoya.pr03.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr03.utils.ValidationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TU CÓDIGO ME HA GUSTADO MUCHO Y HAS MEJORADO MUCHÍSIMO LA LEGIBILIDAD DE TU CÓDIGO RESPECTO
    // A LA PRÁCTICA ANTERIOR. FELICIDADES. SIGUE ASÍ.
    private ImageView imgAvatar;
    private TextView lblAvatar;
    private final Database database = Database.getInstance();
    private EditText txtName;
    private TextView lblName;
    private EditText txtEmail;
    private TextView lblEmail;
    private EditText txtPhonenumber;
    private TextView lblPhonenumber;
    private EditText txtAddress;
    private TextView lblAddress;
    private EditText txtWeb;
    private TextView lblWeb;
    private ImageView imgPhonenumber;
    private ImageView imgEmail;
    private ImageView imgWeb;
    private ImageView imgAddress;
    private ConstraintLayout constraitLayout;

    // QUITA LOS COMENTARIOS TODO CUANDO LOS HAGAS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // TODO
    }

    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if form is valid or not and shows a Snackbar accordingly
     **/
    private void save() {
        // TE HA FALTADO CERRAR EL TECLADO VIRTUAL.
        if (!validateAll()) {
            Snackbar.make(constraitLayout, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(constraitLayout, R.string.main_saved_succesfully, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * InitViews
     */
    private void initViews() {
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        constraitLayout = ActivityCompat.requireViewById(this, R.id.main_constraint);

        // UTILIZA MEJOR UN LAMBDA. HACE QUE EL CÓDIGO SEA MÁS LEGIBLE.
        imgAvatar.setOnClickListener(this);
        lblAvatar.setOnClickListener(this);
        txtName.setOnFocusChangeListener((v, hasFocus) -> setBold(txtName, lblName));
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> setBold(txtEmail, lblEmail));
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setBold(txtPhonenumber, lblPhonenumber));
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> setBold(txtAddress, lblAddress));
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> setBold(txtWeb, lblWeb));

        imgPhonenumber.setTag(R.drawable.ic_call_24dp);
        imgEmail.setTag(R.drawable.ic_email_24dp);
        imgWeb.setTag(R.drawable.ic_web_24dp);
        imgAddress.setTag(R.drawable.ic_map_24dp);
        imgAvatar.setTag(database.getDefaultAvatar().getImageResId());
        lblAvatar.setTag(database.getDefaultAvatar().getName());

        // PODRÍAS USAR UN ÚNICO GestorTextWatcher ¿?
        txtName.addTextChangedListener(new GestorTextWatcher());
        txtEmail.addTextChangedListener(new GestorTextWatcher());
        txtPhonenumber.addTextChangedListener(new GestorTextWatcher());
        txtAddress.addTextChangedListener(new GestorTextWatcher());
        txtWeb.addTextChangedListener(new GestorTextWatcher());

        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });
    }

    // YO LE PASARÍA UN BOOLEANO INDICATIVO DE SI TIENE EL FOCO, EN VEZ DE CONSULTARLO DENTRO
    private void setBold(EditText editText, TextView label) {
        if(editText.hasFocus()) {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == imgAvatar.getId() || v.getId() == lblAvatar.getId()) {
            changeImageAvatar();
        }
    }

    private void changeImageAvatar() {
        Avatar avatarRandom = database.getRandomAvatar();
        // MEJOR CREA UN MÉTODO CON ESTAS CUATRO LÍNEAS showAvatar(avatarRandom)
        imgAvatar.setImageResource(avatarRandom.getImageResId());
        lblAvatar.setText(avatarRandom.getName());
        imgAvatar.setTag(avatarRandom.getImageResId());
        lblAvatar.setTag(avatarRandom.getName());
    }

    // ME GUSTA MUCHO QUE PROPONGAS ESTA SOLUCIÓN PARA HACER EL CÓDIGO MÁS LEGIBLE.
    // IN ENGLISH PLEASE
    private class GestorTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkCurrentView();
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkCurrentView();
        }
    }

    private void checkName() {
        // SIEMPRE QUE DEBAS COMPROBAR SI UNA CADENA ESTÁ VACÍA O SI DOS CADENAS SON IGUALES
        // USA LA CLASE DE UTILIDAD TextUtils Y SUS MÉTODOS .isEmpty() y .equals() PORQUE DE ESA
        // MANERA TE AHORRAS COMPROBAR QUE LA CADENA O CADENAS NO SON NULAS.
        // PODRÍAS HABER CREADO UN MÉTODO isValidName(cadena). DE ESTA MANERA SI EN EL FUTURO
        // HAY MÁS CODICIONES DE VALIDACIÓN PARA EL NOMBRE SÓLO DEBERÍA MODIFICAR DICHO MÉTODO.
        // LO MISMO EN EL RESTO DE MÉTODOS check...
        if(txtName.getText().toString().isEmpty()) {
            txtName.setError(getString(R.string.main_invalid_data));
            lblName.setEnabled(false);
        } else {
            txtName.setError(null);
            lblName.setEnabled(true);
        }
    }

    private void checkEmail() {
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())
                || txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError(getString(R.string.main_invalid_data));
            imgEmail.setEnabled(false);
            lblEmail.setEnabled(false);
        } else {
            txtEmail.setError(null);
            imgEmail.setEnabled(true);
            lblEmail.setEnabled(true);
        }
    }

    private void checkPhonenumber() {
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())
                || txtPhonenumber.getText().toString().isEmpty()) {
            txtPhonenumber.setError(getString(R.string.main_invalid_data));
            imgPhonenumber.setEnabled(false);
            lblPhonenumber.setEnabled(false);
        } else {
            txtPhonenumber.setError(null);
            imgPhonenumber.setEnabled(true);
            lblPhonenumber.setEnabled(true);
        }
    }

    private void checkAddress() {
        if (txtAddress.getText().toString().isEmpty()) {
            txtAddress.setError(getString(R.string.main_invalid_data));
            imgAddress.setEnabled(false);
            lblAddress.setEnabled(false);
        } else {
            txtAddress.setError(null);
            imgAddress.setEnabled(true);
            lblAddress.setEnabled(true);
        }
    }

    private void checkWeb() {
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())
                || txtWeb.getText().toString().isEmpty()) {
            txtWeb.setError(getString(R.string.main_invalid_data));
            imgWeb.setEnabled(false);
            lblWeb.setEnabled(false);
        } else {
            txtWeb.setError(null);
            imgWeb.setEnabled(true);
            lblWeb.setEnabled(true);
        }
    }

    private void checkCurrentView() {
        // getCurrentFocus() PUEDE RETORNAR NULL TEÓRICAMENTE, ASÍ QUE PROTÉGE EL ACCESO
        // CON UNA COMPROBACIÓN DE SI getCurrenFocus() != null
        if(getCurrentFocus().getId() == txtName.getId()) {
            checkName();
        } else if (getCurrentFocus().getId() == txtEmail.getId()) {
            checkEmail();
        }  else if (getCurrentFocus().getId() == txtPhonenumber.getId()) {
            checkPhonenumber();
        } else if (getCurrentFocus().getId() == txtAddress.getId()) {
            checkAddress();
        } else if (getCurrentFocus().getId() == txtWeb.getId()) {
            checkWeb();
        }
    }

    private void checkAll() {
        checkName();
        checkEmail();
        checkPhonenumber();
        checkAddress();
        checkWeb();
    }

    private boolean validateAll() {
        checkAll();
        // AUNQUE ESTO SE FUNCIONA, TIENE UN LEVE PROBLEMA. TE SUPEDITANDO LA VALIDEZ DEL FORMULARIO
        // AL HECHO DE QUE LOS TextView SON DESHABILITADOS SI EL EditText CORRESPONDIENTE NO ES
        // VÁLIDO. ¿PERO QUÉ PASARÍA SI DICHA FUNCIONALIDAD ES ELIMINDADA EN EL FUTURO? BAJO MI
        // PUNTO DE VISTA LA FUNCIÓN DE VALIDACIÓN SÓLO DEBE TRABAJAR CON LOS DATOS DE LOS EditText.
        View[] array = new View[]{lblName, lblEmail, lblPhonenumber, lblAddress, lblWeb};
        for (View view: array) {
            if(!view.isEnabled()) {
                return false;
            }
        }
        return true;
    }
}
