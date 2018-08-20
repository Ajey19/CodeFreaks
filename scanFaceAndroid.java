private void scanFaces() throws Exception {
        Bitmap bitmap = decodeBitmapUri(this, imageUri);
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.rgb(255, 61, 61));
            paint.setTextSize((int) (14 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            scanResults.setText(null);
            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);
                scanResults.setText(scanResults.getText() + "Face " + (index + 1) + "\n");
                scanResults.setText(scanResults.getText() + "Smile probability:" + "\n");
                scanResults.setText(scanResults.getText() + String.valueOf(face.getIsSmilingProbability()) + "\n");
                scanResults.setText(scanResults.getText() + "Left Eye Open Probability: " + "\n");
                scanResults.setText(scanResults.getText() + String.valueOf(face.getIsLeftEyeOpenProbability()) + "\n");
                scanResults.setText(scanResults.getText() + "Right Eye Open Probability: " + "\n");
                scanResults.setText(scanResults.getText() + String.valueOf(face.getIsRightEyeOpenProbability()) + "\n");
                scanResults.setText(scanResults.getText() + "---------" + "\n");

                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x);
                    int cy = (int) (landmark.getPosition().y);
                    canvas.drawCircle(cx, cy, 5, paint);
                }
            }

            if (faces.size() == 0) {
                scanResults.setText("Scan Failed: Found nothing to scan");
            } else {
                imageView.setImageBitmap(editedBitmap);
                scanResults.setText(scanResults.getText() + "No of Faces Detected: " + "\n");
                scanResults.setText(scanResults.getText() + String.valueOf(faces.size()) + "\n");
                scanResults.setText(scanResults.getText() + "---------" + "\n");
            }
        } else {
            scanResults.setText("Could not set up the detector!");
        }
    }
