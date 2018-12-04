# TIX API MongoDB Archetype Reactive
Source Project of TIX API Spring Project Maven Archetype with RX Java 2.0 support
Contains all sets of required modules for development

This repository not used for bare skeleton project for getting started, you need to do some steps before doing the actual project.


Following are the steps to make archetype for Maven : 
1. <code>git clone https://github.com/tiket/ARCHETYPE-MONGODB-REACTIVE-SOURCE</code>
2. <code>cd archetype-mongodb</code>
3. <code>mvn clean install</code>
4. <code>mvn archetype:create-from-project</code>
5. <code>cd target/generated-sources/archetype</code>
6. <code>mvn install</code>
7. Done!!


Now you can create a new Maven Project by just running <code>mvn archetype:generate -DarchetypeCatalog=local</code>

Make sure you select the <code>com.tl.booking.archetype.mongodb</code>, if you have multiple archetype in your local system when you got prompted to select archetype.

Following are the modes of image cropping :
1. <code>scale</code>  
Change the size of the image exactly to the given width and height without necessarily retaining the original aspect ratio:
all original image parts are visible but might be stretched or shrunk.
2. <code>fit</code> 
The image is resized so that it takes up as much space as possible within a bounding box defined by the given width and height
parameters. The original aspect ratio is retained and all of the original image is visible.
3. <code>limit</code>  
Same as the <code>fit</code> mode but only if the original image is larger than the given limit (width and height), in which case the image is scaled down so that it takes up as much space as possible within a bounding box defined by the given width and height parameters. The original aspect ratio is retained and all of the original image is visible. This mode doesn't scale up the image if your requested dimensions are larger than the original image
4. <code>mfit</code>  
Same as the <code>fit</code> mode but only if the original image is smaller than the given minimum (width and height), in which case the image is scaled up so that it takes up as much space as possible within a bounding box defined by the given width and height parameters. The original aspect ratio is retained and all of the original image is visible. This mode doesn't scale down the image if your requested dimensions are smaller than the original image's.
5. <code>fill</code>  
Create an image with the exact given width and height while retaining the original aspect ratio, using only part of the image that fills the given dimensions if necessary (only part of the original image might be visible if the requested aspect ratio is different from the original aspect ratio).
6. <code>lfill</code>  
Same as the <code>fill</code> mode but only if the original image is larger than the given limit (width and height), in which case the image is scaled down to fill the given width and height while retaining the original aspect ratio, using only part of the image that fills the given dimensions if necessary (only part of the original image might be visible if the requested aspect ratio is different from the original aspect ratio).
7. <code>pad</code>  
Resize the image to <code>fill</code> the given width and height while retaining the original aspect ratio and with all of the original image visible. If the proportions
of the original image do not match the given width and height, padding is added to the image to reach the required size.
8. <code>lpad</code>  
Same as the <code>pad</code> mode but only if the original image is larger than the given limit (width and height), in which case the image is scaled down to fill the given width and height while retaining the original aspect ratio and with all of the original image visible. This mode doesn't scale up the image if your requested
dimensions are bigger than the original image's. If the proportions of the original image do not match the given width and height, padding is added to the image to reach the required size.
9. <code>m_pad</code>  
Same as the <code>pad</code> mode but only if the original image is smaller than the given minimum (width and height), in which case the image is scaled up to fill the given width and height while retaining the original aspect ratio and with all of the original image visible. This mode doesn't scale down the image if your requested dimensions are smaller than the original image's. If the proportions of the original image do not match the given width and height, padding is added to the image
to reach the required size.
10. <code>crop</code>  
Extract a region of the given width and height out of the original image. The original proportions are retained and so is the size of the graphics.
11. <code>thumb</code>  
The <code>thumb</code> cropping mode is specifically used for creating image thumbnails from either face or custom coordinates, and must always be accompanied by the gravity parameter set to one of the face detection or custom values. This cropping mode generates a thumbnail of an image with the exact given width and height dimensions and with the original proportions retained, but the resulting image might be scaled to fit in the given dimensions.
