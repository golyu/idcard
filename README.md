  因为数据存储使用了郭霖的litepal，所以你还需要在gradle配置中加入
  `dependencies {
  
      compile 'org.litepal.android:core:1.3.0'
  }
  `
  
  `/**
   * 得到串口对象
   */
  public SerialPort getSerialPort() {
      //波特率   串口号     是否new一个新的，false则从缓存中找
      return SerialPortHelper.getSerialPort(115200, SerialPort.ttyMT3, true);
  }

  /**
   * 打开id电源
   */
  public void openGPIOPowerId() {
      PowerOperate.getInstance().enableRfId();
  }

  /**
   * 打开info电源
   */
  public void openGPIOPowerInfo() {
      PowerOperate.getInstance().enableRfInfo();
  }

  /**
   * 关闭电源
   */
  public void closeGPIOPower() {
      PowerOperate.getInstance().disableGPIO();
  }

  /**
   * 读取身份证id
   */
  public void readIDCardId() {
      CardHelper.getCardId(getSerialPort(), 30, new ReadIdResult() {
          @Override
          public void onSuccess(String cardId) {
              //成功
          }

          @Override
          public void onFailure(int errorMessage) {
              //失败
          }
      });
  }

  /**
   * 读取身份证信息
   */
  public void readIDCardInfo() {
      CardHelper.getCardInfo(getSerialPort(), 30, new ReadInfoResult() {
          @Override
          public void onSuccess(IDCard idCard) {
              //成功
          }

          @Override
          public void onFailure(int errorMessage) {
              //失败
          }

          @Override
          public void onFindCardSuccess() {
              //寻卡成功
          }

          @Override
          public void onSelectCardSuccess() {
              //选卡成功
          }
      });
  }
  `