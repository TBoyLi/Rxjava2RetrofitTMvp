# Rxjava2RetrofitTMvp
Rxjava2 Retrofit 简单封装 自定义拦截器，中断请求等 

这篇文章主要是针对前一篇文章（<a href="https://www.jianshu.com/p/50a33f56cb64">Android TMvp+RxJava+Retrofit 封装</a>）的优化和升级

OK 、 老规矩直接上图，看着图来说话

![总览.gif](https://upload-images.jianshu.io/upload_images/1780580-dbdecb32420d93aa.gif?imageMogr2/auto-orient/strip)![中断请求.gif](https://upload-images.jianshu.io/upload_images/1780580-0510c815ee9029e1.gif?imageMogr2/auto-orient/strip)

简书供上：<a href="https://www.jianshu.com/p/8a9f2f9d4bb2"> Rxjava2RetrofitTMvp </a>

至于为什么要优化呢。主要是因为如下：
***
 第一、mvp上一篇文章呢代码呢在项目中rxjava的生命周期写着麻烦，现加入了rxlifecycle2来简化，主要参考<a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/base/RxActivity.java">RxActivity.java 文件</a> 让BaseActivity继承它即可，使用起来方便
```
private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();


    /**
     *  基本的网络请求都是向服务器请求数据，客户端拿到数据后更新UI。但也不排除意外情况，比如请求回数据途中Activity已经不在了，这个时候就应该取消网络请求。
     *  要实现上面的功能其实很简单，两部分
     *
     *  随时监听Activity(Fragment)的生命周期并对外发射出去； 在我们的网络请求中，接收生命周期
     *  并进行判断，如果该生命周期是自己绑定的，如Destory，那么就断开数据向下传递的过程
     *  实现以上功能需要用到Rxjava的lifecycleSubject
     *
     */
  
```
***
第二、Rxjava中在<a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/ProgressSubscriber.java">ProgressSubscriber.java 文件</a> 构造方法中加 isShowLoading方法来控制是否显示loading dialog，在实际项目中可能会第一次进入界面加载数据显示dialog 刷新和加载更多呢不用显示dialog。而此参数满足了其需求
```
public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShowLoading) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowLoading = isShowLoading;
        this.mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

```
***
第三、Retrofit 加入了拦截器（包括头，请求体，返回数据）和cookie机制，拦截器方便调试中查阅网络请求的问题，至于cookie机制就不用多说了，无非就是解决用户重复登录而已。具体参考<a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/HeaderInterceptor.java">HeaderInterceptor 头部拦截器</a>  <a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/LoggerInterceptor.java">LoggerInterceptor 请求拦截器</a>
请看 
```
D/ProgressSubscriber: onSubscribe: 
V/LoggerInterceptor: POST
                      https://api.douban.com/v2/movie/top250
                      count=10
                      start=1
                      消耗时间：2036.0609ms
                      {
                       "count": 20,
                        "start": 0,
                        "total": 250,
                        "subjects": [
                        {
                         "rating": {
                          "max": 10,
                           "average": 9.6,
                           "stars": "50",
                           "min": 0
                         },
                          "genres": [
                          "\u72af\u7f6a",
                           "\u5267\u60c5"
                         ],
                          "title": "\u8096\u7533\u514b\u7684\u6551\u8d4e",
                          "casts": [
                          {
                           "alt": "https:\/\/movie.douban.com\/celebrity\/1054521\/",
                           "avatars": {
                           "small": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p17525.jpg",
                            "large": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p17525.jpg",
                            "medium": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p17525.jpg"
                          },
                           "name": "\u8482\u59c6\u00b7\u7f57\u5bbe\u65af",
                           "id": "1054521"
                         },
                          {
                          "alt": "https:\/\/movie.douban.com\/celebrity\/1054534\/",
                           "avatars": {
                           "small": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p34642.jpg",
                            "large": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p34642.jpg",
                            "medium": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p34642.jpg"
                          },
                           "name": "\u6469\u6839\u00b7\u5f17\u91cc\u66fc",
                           "id": "1054534"
                         },
                          {
                          "alt": "https:\/\/movie.douban.com\/celebrity\/1041179\/",
                           "avatars": {
                           "small": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p5837.jpg",
                            "large": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p5837.jpg",
                            "medium": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p5837.jpg"
                          },
                           "name": "\u9c8d\u52c3\u00b7\u5188\u987f",
                           "id": "1041179"
                         }
                        ],
                         "collect_count": 1247012,
                         "original_title": "The Shawshank Redemption",
                         "subtype": "movie",
                         "directors": [
                         {
                          "alt": "https:\/\/movie.douban.com\/celebrity\/1047973\/",
                           "avatars": {
                          "small": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p230.jpg",
                           "large": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p230.jpg",
                           "medium": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p230.jpg"
                         },
                          "name": "\u5f17\u5170\u514b\u00b7\u5fb7\u62c9\u90a6\u7279",
                          "id": "1047973"
                        }
                       ],
                        "year": "1994",
                        "images": {
                        "small": "https://img3.doubanio.com\/view\/photo\/s_ratio_poster\/public\/p480747492.jpg",
                         "large": "https://img3.doubanio.com\/view\/photo\/s_ratio_poster\/public\/p480747492.jpg",
                         "medium": "https://img3.doubanio.com\/view\/photo\/s_ratio_poster\/public\/p480747492.jpg"
                         },
                          "alt": "https:\/\/movie.douban.com\/subject\/1292052\/",
                          "id": "1292052"
                        },
                         {
                         "rating": {
                          "max": 10,
                           "average": 9.5,
                           "stars": "50",
                           "min": 0
                         },
                          "genres": [
                          "\u5267\u60c5",
                           "\u7231\u60c5",
                           "\u540c\u6027"
                         ],
                          "title": "\u9738\u738b\u522b\u59ec",
                          "casts": [
                          {
                           "alt": "https:\/\/movie.douban.com\/celebrity\/1003494\/",
                            "avatars": {
                            "small": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p67.jpg",
                             "large": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p67.jpg",
                             "medium": "https://img1.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p67.jpg"
                           },
                            "name": "\u5f20\u56fd\u8363",
                            "id": "1003494"
                          },
                          {
                          "alt": "https:\/\/movie.douban.com\/celebrity\/1050265\/",
                           "avatars": {
                           "small": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p46345.jpg",
                            "large": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p46345.jpg",
                            "medium": "https://img3.doubanio.com\/view\/celebrity\/s_ratio_celebrity\/public\/p46345.jpg"
                          },
                           "name": "\u5f20\u4e30\u6bc5",
                           "id": "1050265"
                         },
                          {
                          "alt": "https:\/\/movie.douban
D/ProgressSubscriber: onNext: 
D/ProgressSubscriber: onComplete: 
```
是不是很方便，这是请求成功，至于错误信息也来张截图，但是这不是ProgressSubscriber 截断出来的。待会介绍

***
第三、错误信息更加智能化，自定义错误信息更方便主要参考<a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/ProgressSubscriber.java">ProgressSubscriber文件</a>    <a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/ApiException.java">ApiException 文件</a> 
自定义 ApiException
```
public enum ApiException {

    /**
     * 解析数据失败
     **/
    PARSE_ERROR,

    /**
     * 网络问题
     **/
    BAD_NETWORK,

    /**
     * 连接错误
     **/
    CONNECT_ERROR,

    /**
     * 连接超时
     **/
    CONNECT_TIMEOUT,

    /**
     * section token past due 过期
     **/
    TOKEN_PAST_DUE,

    /**
     * 未知错误
     **/
    UNKNOWN_ERROR,
}
```
发起异常信息
```
    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.getMessage());
        dismissProgressDialog();
        if (e instanceof SocketException) {
            onException(context, CONNECT_ERROR);
        } else if (e instanceof HttpException) {
            //   HTTP错误
            onException(context, BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(context, CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(context, CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(context, PARSE_ERROR);
        } else {
            onException(context, UNKNOWN_ERROR);
        }
    }

   /**
     * 回调异常
     *
     * @param reason
     */
    private void onException(Context context, ApiException reason) {

        switch (reason) {
            case CONNECT_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_error));
                break;
            case CONNECT_TIMEOUT:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_timeout));
                break;
            case BAD_NETWORK:
                mSubscriberOnNextListener.onFail(context.getString(R.string.bad_network));
                break;
            case PARSE_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.parse_error));
                break;
            case UNKNOWN_ERROR:
            default:
                mSubscriberOnNextListener.onFail(context.getString(R.string.unknown_error));
                break;
        }
    }

错误信息：（断网错误）
03-22 20:53:47.442 19620-19620/com.redli.rxjava2retrofittmvp D/ProgressSubscriber: onSubscribe: 
03-22 20:53:47.505 19620-19620/com.redli.rxjava2retrofittmvp E/onError: Network is unreachable
```

可根据自己需求而定义 （ 主code返回信息来判断 ）

***
第四、中断请求处理，其实我看上面的打印日志就只知道 observer 的执行顺序了，在没有错误的情况下 onSubscribe → onNext → onComplete ，有错的情况下 onSubscribe → onError ，中断请求是怎么做到的呢。我们具体看 onSubscribe 方法的参数 Disposable 类，其实说白了Disposable 就是一个管道而已，链接上游observable 和 下游 observer的，中断就是让这个管道破裂。让下游的消息接受打到上游的消息，但是，上游是发着消息的哦。看<a href="https://github.com/TBoyLi/Rxjava2RetrofitTMvp/blob/master/app/src/main/java/com/redli/rxjava2retrofittmvp/http/rxjava/ProgressSubscriber.java">ProgressSubscriber文件</a>代码说明
```
    @Override
    public void onSubscribe(Disposable s) {
        Log.d(TAG, "onSubscribe: ");
        //获取管道
        this.disposable = s; 
        if (isShowLoading) {
            showProgressDialog();
        }
    }

    @Override
    public void onCancelProgress() {
        Log.d(TAG, "onCancelProgress: ");
        //截断信息，下游接受不到信息
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

```
***
第五的话就是Rxjava 和 Retrofi 的版本升级和去除<a href="https://www.jianshu.com/p/50a33f56cb64">第一篇文章</a> 本地存储机制，使 Rxjava + Retrofi + Mvp 更加简洁和更好的拓展

***
最后呢祝大家新年新气象，财运滚滚，欢迎start 和 issue

***
小编来赚个辛苦钱，Thanks
![微信收钱.jpeg](https://upload-images.jianshu.io/upload_images/1780580-423abe0ce9273225.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/200)  ![支付宝收钱.jpeg](https://upload-images.jianshu.io/upload_images/1780580-8452843cd91fffc1.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/200)


