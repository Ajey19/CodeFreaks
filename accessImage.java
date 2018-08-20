protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        try {
	            // When an Image is picked
	            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data)
                {	                // Get the Image from data	 
                Uri currentImage = data.getData();
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	                // Get the cursor
	                Cursor cursor = getContentResolver().query(currentImage,
	                        filePathColumn, null, null, null);
	                // Move to first row
                cursor.moveToFirst();
	 
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                imgDecodableString = cursor.getString(columnIndex);
	                cursor.close();
ImageView imageV = (ImageView) findViewById(R.id.imageV);	               
// Set the Image in ImageView after decoding the String
	                imageV.setImageBitmap(BitmapFactory
	                        .decodeFile(imgDecodableString));
	 
	            } else {
	                Toast.makeText(this, "Image not selected",
	                        Toast.LENGTH_LONG).show();
	            }	         } catch (Exception e) {
            Toast.makeText(this, "Something isn't working.. Sorry", Toast.LENGTH_LONG)
                    .show();
	        }
	 
    }
