package edu.uw.team5.huskymathchallenge.AlgebraCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by PutthidaSR on 12/6/15.
 */
public class AlgebraQuestionList {


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param algQuestionJSON
     * @return
     */
    public static String parseAlgQuestionJSON(String algQuestionJSON, List<AlgebraQuestion> questionList) {
        String reason = null;
        reason = algQuestionJSON;
        if (reason != null) {
            try {
                JSONArray arr = new JSONArray(algQuestionJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    AlgebraQuestion question = new AlgebraQuestion(obj.getString(AlgebraQuestion.QUESTION), obj.getString(AlgebraQuestion.ANSWER1)
                            , obj.getString(AlgebraQuestion.ANSWER2), obj.getString(AlgebraQuestion.ANSWER3),
                            obj.getString(AlgebraQuestion.ANSWER4), obj.getInt(AlgebraQuestion.CORRECT));
                    questionList.add(question);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
