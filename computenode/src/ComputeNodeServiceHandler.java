import org.apache.thrift.TException;
import cpnode.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ComputeNodeServiceHandler implements ComputeNodeService.Iface{
  private static final Size BLUR_SIZE = new Size(3,3);
  private static final int RATIO = 3;
  private static final int KERNEL_SIZE = 3;
  private static final int lowThresh = 20;
  int count = 0;

	public ComputeNodeServiceHandler(){    	
	}
	@Override
	public boolean imgprocess(String filepath){
		System.out.println("Received request");
		System.out.println(filepath);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		return canny_edge_detect(filepath);
	}
	@Override
	public boolean canny_edge_detect(String filepath){
		String[] components = filepath.split("/");
		String filename = components[components.length-1];
		System.out.println(components[components.length-1]);
    		Mat src = Imgcodecs.imread(filepath);
    		Mat srcBlur = new Mat();
    		Mat detectedEdges = new Mat();
    		Mat dst = new Mat();
		Imgproc.blur(src, srcBlur, BLUR_SIZE);
        	Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThresh * RATIO, KERNEL_SIZE, false);
        	dst = new Mat(src.size(), CvType.CV_8UC3, Scalar.all(0));
        	src.copyTo(dst, detectedEdges);
		Imgcodecs.imwrite("output_dir/output_"+ filename,dst);
		count++;
		return true;

	}

}	
