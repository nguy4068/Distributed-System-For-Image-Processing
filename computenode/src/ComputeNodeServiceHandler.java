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
/**
 *ComputeNodeServiceHandler is the worker of compute node
 */
public class ComputeNodeServiceHandler implements ComputeNodeService.Iface{
        //Declare essential variables for canny edge detection function
	private static final Size BLUR_SIZE = new Size(3,3);
        private static final int RATIO = 3;
        private static final int KERNEL_SIZE = 3;
        private static final int lowThresh = 20;
        double inject_prob;//probability that the node will delay
	/**
	 *Constructor takes in inject_prob
	 */
	public ComputeNodeServiceHandler(double inject_prob){
		this.inject_prob = inject_prob;	
	}
	/**
	 *this function will take in filepath and output canny edge detected
	 *image
	 *@param filepath the path name of the image
	 *@return true if image process is done successfully false otherwise
	 */
	@Override
	public boolean imgprocess(String filepath){
		System.out.println("Received request " + filepath);
		System.out.println(filepath);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		double random = Math.random();
		if (random <= inject_prob){
			//should inject here
			System.out.println("Delay job");
			try{
				Thread.sleep(3000);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return canny_edge_detect(filepath);
	}
	@Override
	public boolean canny_edge_detect(String filepath){
		System.out.println(filepath);
		String[] components = filepath.split("/");
		String filename = components[components.length-1];
		System.out.println(components[components.length-1]);
		try{
    			Mat src = Imgcodecs.imread(filepath);
    			Mat srcBlur = new Mat();
    			Mat detectedEdges = new Mat();
    			Mat dst = new Mat();
			Imgproc.blur(src, srcBlur, BLUR_SIZE);
        		Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThresh * RATIO, KERNEL_SIZE, false);
        		dst = new Mat(src.size(), CvType.CV_8UC3, Scalar.all(0));
        		src.copyTo(dst, detectedEdges);
			Imgcodecs.imwrite("output_dir/output_"+ filename,dst);
			System.out.println("Done writing image for " + filename);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

}	
