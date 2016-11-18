package edu.temple.fourcolorgame.Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//Surface for the puzzle
public class Surface extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bitmap;
    private Context context;
    private SurfaceHolder holder;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void construct(Context context){
        this.context = context;
        holder = getHolder();
        holder.addCallback(Surface.this);
    }

    public Surface(Context context){
        super(context);
        construct(context);
        bitmap = null;

    }

    public Surface(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        construct(context);
        bitmap = null;
    }

    public Surface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        construct(context);
        bitmap = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        if(bitmap != null){
            draw(bitmap);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void draw(Bitmap bitmap){
        Canvas canvas = holder.lockCanvas();
        if(canvas == null){
            return;
        } else {
            canvas.drawBitmap(bitmap, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
