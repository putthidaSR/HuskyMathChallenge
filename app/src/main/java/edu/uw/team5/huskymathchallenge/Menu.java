package edu.uw.team5.huskymathchallenge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;

import edu.uw.team5.huskymathchallenge.AlgebraCategory.AlgebraActivity;
import edu.uw.team5.huskymathchallenge.AllAboutMath.AllAboutMath;
import edu.uw.team5.huskymathchallenge.CalculusCategory.CalculusQuizActivity;
import edu.uw.team5.huskymathchallenge.GeometryCategory.GeometryQuizActivity;


/**
 * This class represents the Menu screen.
 *
 * @author Putthida Samrith and Le Phu Bui
 * @version December 6, 2015
 */
public class Menu extends AppCompatActivity {

    /**
     * Attribute
     */
    private Button mChooseCategory;
    private Button mInstruction;

    /**
     * This method is where to initialize the activity, and field.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mChooseCategory = (Button) findViewById(R.id.choose_category);
        mChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence category[] = new CharSequence[]{"Algebra", "Geometry", "Calculus", "All About Math"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                builder.setTitle("Please choose the category!");
                builder.setItems(category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int categoryNum) {
                        if (categoryNum == 0) {
                            Intent intent = new Intent(Menu.this, AlgebraActivity.class);
                            startActivity(intent);
                        } else if (categoryNum == 1) {
                            Intent intent = new Intent(Menu.this, GeometryQuizActivity.class);
                            startActivity(intent);
                        } else if (categoryNum == 2) {
                            Intent intent = new Intent(Menu.this, CalculusQuizActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Menu.this, AllAboutMath.class);
                            startActivity(intent);
                        }

                    }
                });
                builder.show();

            }
        });

        mInstruction = (Button) findViewById(R.id.instruction_button);
        mInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Menu.this, InstructionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
    }

    /**
     * This method contains implementation of log out.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            LoginManager.getInstance().logOut();

            Intent intent = new Intent(Menu.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
