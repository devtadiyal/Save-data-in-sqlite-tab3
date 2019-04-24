package com.kk.dialer.contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.sip.SipManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.kk.dialer.DialpadActivity;
import com.kk.dialer.R;


public class Phonebook extends Fragment {
	
	SimpleCursorAdapter mAdapter;
	MatrixCursor mMatrixCursor;	
	public ProgressDialog progress;
	View v;
	public static long contactId;
	ToggleButton TOGGLE_BUTTON;
	public static File tmpFile;
	public static int cc;
	Cursor dataCursor ;
	ImageView myImage;
	Bitmap bitmap ;
	Mythread m;
	
	public static int indexnum=0;
	String [] n;
	public static ArrayList<Long> al = new ArrayList();
	public static ArrayList<String> al3 = new ArrayList();
	ArrayList<Long> al2 = new ArrayList();
	private DigitsEditText digits;
	TextView number,name;
	private static final int PICK_CONTACT = 0;
	public static String no,nme,no1,no2,no3,myBitmap,a;
	public static long photoid = 0L;
	ConnectivityManager connMgr;
	NetworkInfo networkInfo;
	private Context c;
	File cacheDirectory;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
       //super.onCreateView(inflater, container, savedInstanceState);
		 v = inflater.inflate(R.layout.contactlist, container, false);
		c = getActivity().getBaseContext();
		
		 connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

	        networkInfo = connMgr.getActiveNetworkInfo();
	        // The contacts from the contacts content provider is stored in this cursor
	        mMatrixCursor = new MatrixCursor(new String[] { "_id","name","photo","details"} );
	        
	        // Adapter to set data in the listview
	        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
	                R.layout.lv_layout, 
	                null,
	                new String[] { "name","photo","details"},
	                new int[] { R.id.tv_name,R.id.iv_photo,R.id.tv_details}, 0);
	        
	        // Getting reference to listview
	        ListView lstContacts = (ListView) v.findViewById(R.id.lst_contacts);
	        
	        // Setting the adapter to listview
	        lstContacts.setAdapter(mAdapter);   
	        System.out.println("DEVTADIYAL"+mAdapter);
	        
	        
	        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	 	          @SuppressWarnings("deprecation")
				@Override
	 	          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
	 	        	  
	 	        	 try { 	        		
					try { 
							Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
							Cursor contactsCursor = getActivity().managedQuery(contactsUri, null, null, null, 
									ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
							
							if (contactsCursor != null && contactsCursor.moveToFirst());
						   do {
							
						   contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
						    al.add(contactId);	//photoid 
						   
						    } while (contactsCursor.moveToNext());
						   System.out.println(al.size());
						   
						   
						   for(long j = 0; j < al.size(); j++)
		 	               {
		 	                 al2.add(j); // indexing value
		 	               }
						   System.out.println("INDEX VALUE "+al2);
						   System.out.println("PHOTO ID "+al);
						  
					}
					  catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 	        		  
	 	        		  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+contactId);
	 	        		  
						 myImage = (ImageView)view.findViewById(R.id.iv_photo);
						number=(TextView)view.findViewById(R.id.tv_details);
						 name=(TextView)view.findViewById(R.id.tv_name);
						
						 no = number.getText().toString();
						System.out.println("DEV  "+no);
						 a = no.replaceAll("[A-Za-z: ]", "");
						System.out.println("QQQQQQQQQQQQQQQQ "+a);
						
						 Intent it = new Intent(getActivity(), DialpadActivity.class);

						/* it.setAction(SipManager.ACTION_SIP_DIALER);
						 it.setData(SipUri.forgeSipUri(SipManager.PROTOCOL_SIP, ""));*/
						 startActivity(it);


						 n = no.split("\n");
						  System.out.println("1"+n[0]);
						  
						  indexnum = i;
						  System.out.println(indexnum);
						  
						
						     no1 = n[0];
					
						     nme = name.getText().toString();
						    System.out.println("CONTACT NUMBER "+nme);
						    //  Toast.makeText(getActivity(),"Item Clicked: "+nme,Toast.LENGTH_SHORT).show();
						 
						 /* Intent ai = new Intent (getActivity(),ShowContact.class);
						  startActivity(ai);*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						no1 = n[0];
						
						  
						     nme = name.getText().toString();
						  //  System.out.println(nme);
						/* Intent ai = new Intent (getActivity(),ShowContact.class);
						  startActivity(ai);*/
					}
	 	          }
	 	      });
	        
	         m = new Mythread();
	        m.start();
	     /*   // Creating an AsyncTask object to retrieve and load listview with contacts
	        ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
	        
	        // Starting the AsyncTask process to retrieve and load listview with contacts
	        listViewContactsLoader.execute();       */
	   
		return v;
		
	}
    
	class Mythread extends Thread
	{
		public void run()
		{
			// Creating an AsyncTask object to retrieve and load listview with contacts
	        ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
	        
	        // Starting the AsyncTask process to retrieve and load listview with contacts
	        listViewContactsLoader.execute();  
		}
	}
	
    /** An AsyncTask class to retrieve and load listview with contacts */
    private class ListViewContactsLoader extends AsyncTask<Void, Void, Cursor>{   	

		@Override
		protected Cursor doInBackground(Void... params) {
			try {
				Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
				
				// Querying the table ContactsContract.Contacts to retrieve all the contacts
				Cursor contactsCursor = getActivity().getContentResolver().query(contactsUri, null, null, null, 
										ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
				
			
				if(contactsCursor.moveToFirst()){
					do{
						long contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
						photoid = contactId;
						
						Uri dataUri = ContactsContract.Data.CONTENT_URI;
						
						// Querying the table ContactsContract.Data to retrieve individual items like
						// home phone, mobile phone, work email etc corresponding to each contact 
						Cursor dataCursor = getActivity().getContentResolver().query(dataUri, null, 
												ContactsContract.Data.CONTACT_ID + "=" + contactId, 
												null, null);
						
						
						String displayName="";
						//String nickName="";
						//String homePhone="";
						String mobilePhone=""; 
						//String workPhone="";
						String photoPath="" + R.drawable.ic_contact_picture_holo_dark;
						byte[] photoByte=null;
						//String homeEmail="";
						//String workEmail="";
						//String companyName="";
						//String title="";
						
						
						
						if(dataCursor.moveToFirst()){
							// Getting Display Name
							displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME ));
							int ii=0;
							do{
													
								/*// Getting NickName
								if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
									nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));*/
								
								// Getting Phone numbers
								if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){
									switch(dataCursor.getInt(dataCursor.getColumnIndex("data2"))){
										/*case ContactsContract.CommonDataKinds.Phone.TYPE_HOME : 
											homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
											System.out.println("DEVTADIYALHOMEPHONE"+homePhone);
											break;*/
										case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE : 
											mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
											
											if(mobilePhone.length()!=0)
											{
												//System.out.println("DEVTADIYALWORKPHONE"+mobilePhone);
												al3.add(mobilePhone);
											}
											
											else{
												// myImage.setImageResource(R.drawable.ic_action_action_search);
											//	 System.out.println("DEVTADIYALWORKPHONE123");
											}
											//System.out.println(""+al3);
											 // Create a new instance of Gson
											 // Create a new instance of Gson
											String [] countries = al3.toArray(new String[al3.size()]);
											System.out.println("numbers = " + countries);
											Gson gson = new Gson();

									        // Convert numbers array into JSON string.
									        String numbersJson = gson.toJson(countries);

									        // Convert strings array into JSON string
									       
									        System.out.println("numbersJson = " + numbersJson);
									        
											
											break;
										/*case ContactsContract.CommonDataKinds.Phone.TYPE_WORK : 
											workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
											System.out.println("DEVTADIYALWORKPHONE"+workPhone);
											break;	*/
									}
								}
								
								/*// Getting EMails
								if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE ) ) {									
									switch(dataCursor.getInt(dataCursor.getColumnIndex("data2"))){
										case ContactsContract.CommonDataKinds.Email.TYPE_HOME : 
											homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
											break;
										case ContactsContract.CommonDataKinds.Email.TYPE_WORK : 
											workEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
											break;										
									}
								}*/
								
								/*// Getting Organization details
								if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)){
									companyName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
									title = dataCursor.getString(dataCursor.getColumnIndex("data4"));
								}*/
									
								// Getting Photo	
								if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)){								
									photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
									
									if(photoByte != null) {							
										 bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
										
										// Getting Caching directory 
					                    cacheDirectory = getActivity().getBaseContext().getCacheDir();

					                    // Temporary file to store the contact image 
					                    String mynum = "";
					                   
					               	 if(mobilePhone != null)
					               	 {
					               		 mynum = mobilePhone.replaceAll("[^\\d.]", "");
					               		 
					               	 }
					               
					                     tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+photoid+".png");
					                     System.out.println("DEVTADIYALPHOTOes  "+tmpFile.getPath());
					                     
					                  
					                    // The FileOutputStream to the temporary file
					                    try {
											FileOutputStream fOutStream = new FileOutputStream(tmpFile);
											
											// Writing the bitmap to the temporary file as png file
						                    bitmap.compress(Bitmap.CompressFormat.PNG,100, fOutStream);

						                    // Flush the FileOutputStream
						                    fOutStream.flush();

						                    //Close the FileOutputStream
						                    fOutStream.close();

										} catch (Exception e) {
											e.printStackTrace();
										}

					                    photoPath = tmpFile.getPath();
									}
								
								}
								
							}while(dataCursor.moveToNext());					
							
							String details = "",str3="";
							
							// Concatenating various information to single string
							//if(homePhone != null && !homePhone.equals("") )
							//	details = "HomePhone : " + homePhone + "\n";
							if(mobilePhone != null && !mobilePhone.equals("") )
								details += "MobilePhone : " + mobilePhone + "\n";
							
							System.out.println("!!!!!!!!!!!!!!!! "+details);
							try {
								File f1 = new File(getActivity().getFilesDir(), "devika.txt");
								try {
									FileWriter fw1 = new FileWriter(f1);
									String s1 = details;//AccountWizard.creditStatus;
									
									
									fw1.write(s1);
									System.out.println(f1.getAbsolutePath());
									
									fw1.close();
									
									BufferedReader bf1 = new BufferedReader(new FileReader(f1));
									String sss1,ss1 = "";
									while((sss1 = bf1.readLine())!=null)
									{
										ss1+=sss1;
										
									}
									System.out.println("VALUE OF Account wizard sladfjlksd "+ss1);
									bf1.close();
									
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									
									System.out.println("EXCEPTION IS "+e);
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							
							 File f111 = new File(getActivity().getFilesDir(), "devika.txt");
								try {
									BufferedReader bf = new BufferedReader(new FileReader(f111));
									String sss;
									
									
									while((sss = bf.readLine())!=null)
									{
										str3+=sss;
									}
									System.out.println(str3+"TIMEOUT HAI VALUE"+str3.length());
									bf.close();
									
									
								} catch (IOException e) {
								
									e.printStackTrace();
								}
							
							
						
							// Adding id, display name, path to photo and other details to cursor
							mMatrixCursor.addRow(new Object[]{ Long.toString(contactId),displayName,photoPath,str3});
						}
						
					}while(contactsCursor.moveToNext());
				}
				System.out.println("HELLO MOTTO  "+mMatrixCursor);
				return mMatrixCursor;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mMatrixCursor;
		}
		
		 protected void onPreExecute() {
			
			 
			 
		    
		   }
		  
		
    	
		@Override
		protected void onPostExecute(Cursor result) {			
			// Setting the cursor containing contacts to listview
			mAdapter.swapCursor(result);
			System.out.println("DEVTADIYAL FINAL RESULT "+result);
		}		
    } 

    public boolean onCreateOptionsMenu(Menu menu) {
       // getActivity().getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   }