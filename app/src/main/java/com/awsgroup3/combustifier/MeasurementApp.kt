package com.awsgroup3.combustifier

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.TransformableNode

private fun tapDistanceOf2Points(hitResult: HitResult){
    if (placedAnchorNodes.size == 0){
        placeAnchor(hitResult, cubeRenderable!!)
    }
    else if (placedAnchorNodes.size == 1){
        placeAnchor(hitResult, cubeRenderable!!)

        val midPosition = floatArrayOf(
            (placedAnchorNodes[0].worldPosition.x + placedAnchorNodes[1].worldPosition.x) / 2,
            (placedAnchorNodes[0].worldPosition.y + placedAnchorNodes[1].worldPosition.y) / 2,
            (placedAnchorNodes[0].worldPosition.z + placedAnchorNodes[1].worldPosition.z) / 2)
        val quaternion = floatArrayOf(0.0f,0.0f,0.0f,0.0f)
        val pose = Pose(midPosition, quaternion)

        placeMidAnchor(pose, distanceCardViewRenderable!!)
    }
    else {
        clearAllAnchors()
        placeAnchor(hitResult, cubeRenderable!!)
    }
}

private fun placeMidAnchor(pose: Pose,
                           renderable: Renderable,
                           between: Array<Int> = arrayOf(0,1)){
    val midKey = "${between[0]}_${between[1]}"
    val anchor = arFragment!!.arSceneView.session!!.createAnchor(pose)
    midAnchors.put(midKey, anchor)

    val anchorNode = AnchorNode(anchor).apply {
        isSmoothed = true
        setParent(arFragment!!.arSceneView.scene)
    }
    midAnchorNodes.put(midKey, anchorNode)

    val node = TransformableNode(arFragment!!.transformationSystem)
        .apply{
            this.rotationController.isEnabled = false
            this.scaleController.isEnabled = false
            this.translationController.isEnabled = true
            this.renderable = renderable
            setParent(anchorNode)
        }
    arFragment!!.arSceneView.scene.addOnUpdateListener(this)
    arFragment!!.arSceneView.scene.addChild(anchorNode)
}
