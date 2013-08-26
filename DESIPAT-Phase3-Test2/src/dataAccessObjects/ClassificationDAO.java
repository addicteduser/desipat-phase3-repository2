package dataAccessObjects;

import Strategy.SelectClassificationStrategy;
import Template.SelectClassificationTemplate;
import Template.SelectQueryTemplate;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Aram
 */
public class ClassificationDAO extends DAO{

	private static ClassificationDAO classificationDAO;

	private ClassificationDAO(){

	}

	public static ClassificationDAO getInstance(){
		if(classificationDAO == null)
			classificationDAO = new ClassificationDAO();

		return classificationDAO;
	}


	public ArrayList<String> getAllClassification() throws SQLException {
		ArrayList<String> Classification = new ArrayList<String>();

		SelectQueryTemplate template = new SelectClassificationTemplate();
		Classification = template.executeQuery(new SelectClassificationStrategy());

		return Classification;
	}

}
