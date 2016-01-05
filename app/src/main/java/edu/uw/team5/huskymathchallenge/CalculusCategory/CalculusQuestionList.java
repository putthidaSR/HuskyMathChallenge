package edu.uw.team5.huskymathchallenge.CalculusCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by lebui on 12/6/2015.
 */
public class CalculusQuestionList {

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param algQuestionJSON
     * @return
     */
    public static String parseAlgQuestionJSON(String algQuestionJSON, List<CalculusQuestion> questionList) {
        String reason = null;
        reason = algQuestionJSON;
        if (reason != null) {
            try {
                JSONArray arr = new JSONArray(algQuestionJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    CalculusQuestion question = new CalculusQuestion(obj.getString(CalculusQuestion.QUESTION), obj.getString(CalculusQuestion.ANSWER1)
                            , obj.getString(CalculusQuestion.ANSWER2), obj.getString(CalculusQuestion.ANSWER3),
                            obj.getString(CalculusQuestion.ANSWER4), obj.getInt(CalculusQuestion.CORRECT));
                    questionList.add(question);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
