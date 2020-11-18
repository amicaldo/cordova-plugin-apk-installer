var exec = require('cordova/exec');

class ApkInstaller {
  /**
   * @param {string} file
   *
   * @return {Promise<any>}
   */
  static install(file) {
    return new Promise((resolve, reject) => {
      exec(resolve, reject, 'ApkInstallerPlugin', 'install', [file]);
    });
  }
}

module.exports = ApkInstaller;
