package jade;

import components.RigidBody;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private GameObject obj2;
    private SpriteSheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");
        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        obj1.addComponent(new RigidBody());
        this.addGameObjectToScene(obj1);

        obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 2);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);

//        int xOffset = 10;
//        int yOffset = 10;
//
//        float totalWidth = (float) (600 - xOffset * 2);
//        float totalHeight = (float) (300 - yOffset * 2);
//        float sizeX = totalWidth / 100.0f;
//        float sizeY = totalHeight / 100.f;
//
//        for (int x = 0; x < 100; x++) {
//            for (int y = 0; y < 100; y++) {
//                float xPos = xOffset + (x * sizeX);
//                float yPos = yOffset + (y * sizeY);
//
//                GameObject go = new GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos),
//                        new Vector2f(sizeX, sizeY)));
//                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight,
//                        1, 1)));
//                this.addGameObjectToScene(go);
//            }
//        }
    }

    private void loadResources() {

        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }

    @Override
    public void update(float dt) {
        MouseListener.getOrthoX();

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowsX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x,
                    texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                System.out.println("Button " + i + " clicked");
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowsX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
