import 'package:flutter/material.dart';
import 'package:flutter_native_admob/flutter_native_admob.dart';
import 'package:flutter_native_admob/native_admob_controller.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const _adUnitID = "ca-app-pub-3940256099942544/2247696110";

  final _nativeAdController = NativeAdmobController();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: NativeAdmob(
          // Your ad unit id
          adUnitID: _adUnitID,
          numberAds: 3,
          controller: _nativeAdController,
          type: NativeAdmobType.banner,
        ),
        floatingActionButton: FloatingActionButton.extended(
          label: Text("Refresh Ads"),
          onPressed: () {
            _nativeAdController.reloadAd(forceRefresh: true);
          },
        ),
      ),
    );
  }
}
