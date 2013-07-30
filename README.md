HTML5DynamicResource
====================

minSdkVersion 11

Yes, link "http://www.mocoven.com/notexist.css" is not exist.
我故意的好嗎

getCssWebResourceResponseFromExternalStorage
	- function: load the CSS from the external storage
	- before you run the App, please push "external-theme.css" file to "mnt/sdcard/files/external-theme.css"
	- "external-theme.css" is under the "assets/www/resource" folder, ready for you
	- image is base64 png format, carried in the css file
	
getCssWebResourceResponseFromAsset
	- function: load the CSS from the asset folder
	
getCssWebResourceResponseFromString
	- function: load the CSS from your own string

