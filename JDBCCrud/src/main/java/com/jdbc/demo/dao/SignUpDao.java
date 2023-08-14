package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response rsp = new Response();

	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String username = "root";
	String password = "Arvindh@2000";

	public Response createUser(SignUpModel values) {
		String uuid = UUID.randomUUID().toString();
		values.setsNo(uuid);
		values.setCreatedBy(uuid);
		values.setUpdatedBy(uuid);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		values.setCreatedDate(date);
		values.setUpdatedDate(date);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				String insertQuery = "INSERT INTO kgm.user_details(s_no,first_name,last_name,gender,dob,mobile_number,email_id,password,created_by,updated_by,created_date,updated_date)"
						+ "VALUES ('" + values.getsNo() + "','" + values.getFirstName() + "','" + values.getLastName()
						+ "','" + values.getGender() + "','" + values.getDob() + "'," + values.getMobileNumber() + ",'"
						+ values.getEmailId() + "','" + values.getPassword() + "','" + values.getCreatedBy() + "','"
						+ values.getUpdatedBy() + "','" + values.getCreatedDate() + "','" + values.getUpdatedDate()
						+ "');";
				st.executeUpdate(insertQuery);
				rsp.setData("User Created Successfully!");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Success");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot create User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}
		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;
	}

	@SuppressWarnings("unchecked")
	public Response getUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from user_details";
			JSONArray jsonArray = new JSONArray();
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuery);) {
				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("dob", rs.getDate("dob"));
					jsonObject.put("mobileNumber", rs.getLong("mobile_number"));
					jsonObject.put("emailId", rs.getString("email_id"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("createdDate", rs.getString("created_date"));

					jsonArray.add(jsonObject);
				}
				rsp.setData("Sucess");
				rsp.setjData(jsonArray);
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Data fetched successfully");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot create User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");

			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");

			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;
	}

	@SuppressWarnings("unchecked")
	public Response getOneUser(String s_no) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String selectQuery = "select * from user_details where s_no = '" + s_no + "'";
			JSONObject jsonObject = new JSONObject();
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuery);) {
				while (rs.next()) {

					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("dob", rs.getDate("dob"));
					jsonObject.put("mobileNumber", rs.getLong("mobile_number"));
					jsonObject.put("emailId", rs.getString("email_id"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("createdDate", rs.getString("created_date"));
				}

				rsp.setData("Sucess");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Data retrived successfully");
				rsp.setjData(jsonObject);
			} catch (Exception e) {

				rsp.setData("Failure");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");

			}
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setData("Driver Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}

		return rsp;

	}

	public Response updateEmail(String email_id, String s_no) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				String updateQuery = "update kgm.user_details set email_id = '" + email_id + "' where s_no = '" + s_no
						+ "';";

				System.out.println(updateQuery);
				st.executeUpdate(updateQuery);

				rsp.setData("Updated Successfully");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Success");

			} catch (Exception e) {
				e.printStackTrace();
				rsp.setData("Error");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Logic Error");

			}

		} catch (Exception e) {
			e.printStackTrace();
			rsp.setData("Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Driver Error");

		}

		return rsp;
	}

	public Response delUser(String s_no) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				String deleteQuery = "Delete from kgm .user_details where s_no = '" + s_no + "'";
				st.executeUpdate(deleteQuery);

				rsp.setData("Deleted Successfully");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Success");

			} catch (Exception e) {

				e.printStackTrace();
				rsp.setData("Error");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Logic Error");

			}

		} catch (Exception e) {

			e.printStackTrace();
			rsp.setData("Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Driver Error");

		}

		return rsp;
	
}

	public Response scamUser(String s_no) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String scamQuery = "update userdetails set is_active=0 where s_no = '"+ s_no +"';";
			
			try(Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				    st.executeUpdate(scamQuery);
				    
				    rsp.setData("Scam Successfully done");
					rsp.setResponseCode(200);
					rsp.setResponseMessage("Success");
					
			} catch (Exception e) {
				
				e.printStackTrace();
				rsp.setData("Error");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Logic Error");
				
				
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
			rsp.setData("Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Driver Error");
			
		}
		
		
		
		return rsp;
	}
	
	
}
