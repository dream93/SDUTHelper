package xyz.myhelper.sduthelper.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
//TODO 自定义圆形控件
public class MyImageView extends ImageView{
	private Paint paint;
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	public MyImageView(Context context) {
		this(context, null);
		initPaint();
	}

	public void initPaint(){
		paint=new Paint();
		paint.setAntiAlias(true);
	}
	//绘制圆形图片
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable=getDrawable();
		if (drawable!=null) {
			Bitmap bitmap=((BitmapDrawable) drawable).getBitmap();
			Bitmap b=getCircleBitmap(bitmap, 14);
			Rect rect=new Rect(0,0,b.getWidth(),b.getHeight());
			Rect rectDest=new Rect(0,0,getWidth(),getHeight());
			paint.reset();
			canvas.drawBitmap(b, rect,rectDest, paint);

		}else {
			super.onDraw(canvas);
		}
	}
	private Bitmap getCircleBitmap(Bitmap bitmap,int pixels){
		Bitmap output=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Config.ARGB_8888);
		Canvas canvas=new Canvas(output);
		final int color=0xff424242;
		final Rect rect=new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		paint.setAntiAlias(true);
		int x=bitmap.getWidth();
		canvas.drawCircle(x/2, x/2, x/2, paint);
		paint.setXfermode(new PorterDuffXfermode((Mode.SRC_IN)));
		canvas.drawBitmap(bitmap, rect,rect, paint);
		return output;

	}
}
