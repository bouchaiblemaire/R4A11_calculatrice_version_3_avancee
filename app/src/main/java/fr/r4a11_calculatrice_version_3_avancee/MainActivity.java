package fr.r4a11_calculatrice_version_3_avancee;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.r4a11_calculatrice_version_3_avancee_corrige.R;


/**
 * Calculatrice utilisable en mode portrait et en mode paysage
 * @version 2022
 */
public class MainActivity extends Activity {


    private static final String OPERANDE_1 = "operande_1";
    private static final String OPERANDE_2 = "operande_2";
    private static final String AFFICHEUR = "afficheur";

    private EditText operande_1;
    private EditText operande_2;

    private TextView tv_afficheur;

    private String s_afficheur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Désérialisations du laytout associé à l'Activity
         */
        setContentView(R.layout.activity_main);


        /**
         * Désérialisation des ressources
         */

        operande_1 = (EditText) findViewById(R.id.et_op1);
        operande_2 = (EditText) findViewById(R.id.et_op2);

        tv_afficheur = (TextView) findViewById(R.id.tv_afficheur);

        Button bt_plus = (Button) this.findViewById(R.id.id_bt_plus);
        Button bt_moins = (Button) this.findViewById(R.id.id_bt_moins);
        Button bt_mult = (Button) this.findViewById(R.id.id_bt_mult);
        Button bt_div = (Button) this.findViewById(R.id.id_bt_div);
        Button bt_ac = (Button) this.findViewById(R.id.id_bt_ac);

        /**
         * Mise en place des écouteurs
         */

        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "MainAcitivty-->Calcul " + ((Button) v).getText().toString());
                calculer(((Button) v).getText().toString());
            }
        });

        bt_moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "MainAcitivty-->Calcul " + ((Button) v).getText().toString());
                calculer(((Button) v).getText().toString());
            }
        });

        bt_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "MainAcitivty-->Calcul " + ((Button) v).getText().toString());
                calculer(((Button) v).getText().toString());
            }
        });

        bt_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "MainAcitivty-->Calcul " + ((Button) v).getText().toString());
                calculer(((Button) v).getText().toString());
            }
        });


        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "MainAcitivty-->Calcul somme");
                calculer(((Button) v).getText().toString());
            }
        });

        bt_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.tv_afficheur.setText("");
                MainActivity.this.operande_1.setText("");
                MainActivity.this.operande_2.setText("");
                MainActivity.this.s_afficheur="";
            }
        });


        /**
         * Initalilisation
         */
        this.s_afficheur = "";
    }


    private void calculer(String s_oper) {
        String s_oper_1 = operande_1.getText().toString();
        String s_oper_2 = operande_2.getText().toString();

        if (s_oper_1.trim().equals("") || s_oper_2.trim().equals("")) {
            Toast.makeText(this, R.string.erreur_operandes_manquantes, Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * Mise à jour de l'afficheur avec les opérandes et l'opérateur
         */

        String s_result = MainActivity.this.calcul(s_oper_1, s_oper_2, s_oper);
        this.s_afficheur =  this.tv_afficheur.getText().toString();
        this.s_afficheur += '\n'+ s_result;
        tv_afficheur.setText(this.s_afficheur);

        /**
         * Effacement des opérandes
         */
        operande_1.setText("");
        operande_2.setText("");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        /**
         * Sauvegarde des opérandes et de l'afficheur
         */
        outState.putString(OPERANDE_1, MainActivity.this.operande_1.getText().toString());
        outState.putString(OPERANDE_2, MainActivity.this.operande_2.getText().toString());
        outState.putString(AFFICHEUR, MainActivity.this.s_afficheur);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        /**
         * Restauration des opérandes et de l'afficheur
         */
        MainActivity.this.operande_1.setText(savedInstanceState.getString(OPERANDE_1));
        MainActivity.this.operande_2.setText(savedInstanceState.getString(OPERANDE_2));
        MainActivity.this.s_afficheur = savedInstanceState.getString(AFFICHEUR);
        MainActivity.this.tv_afficheur.setText(MainActivity.this.s_afficheur);


    }

    /**
     * Methode privée permettant d'effectuer le calcul
     * @param s_oper_1 (String) : première opérande
     * @param s_oper_2 (String) : deuxième opérande
     * @param s_oper (String) : opérateur
     * @return (String) : resultat de l'opération
     */
    private String calcul(String s_oper_1, String s_oper_2,String s_oper){

        String expression =s_oper_1 +s_oper+s_oper_2+" = ";

        if(!s_oper.equals("+")
                &&!s_oper.equals("-")
                &&!s_oper.equals("/")
                &&!s_oper.equals("*")
        )
            expression+="Erreur";
        else

        {
            try {
                double oper1 = Double.parseDouble(s_oper_1);
                double oper2 = Double.parseDouble(s_oper_2);

                double res;
                if (s_oper.equals("+")) {
                    res = oper1 + oper2;
                } else if (s_oper.equals("-")) {
                    res = oper1 - oper2;
                } else if (s_oper.equals("/")) {
                    if (oper2 == 0) throw new ArithmeticException();

                    res = oper1 / oper2;
                } else res = oper1 * oper2;

                expression += " " + res;
            } catch (ArithmeticException e) {
                expression += "Erreur : Division par 0 !";
            } catch (NumberFormatException e) {
                expression += "Erreur";
            }
        }

        return expression;
    }
}
